package com.authentication.service;


import com.authentication.channel.AccessChannel;
import com.authentication.exceptions.codes.ErrorCode;
import com.authentication.identityprovider.AuthenticationProvider;
import com.authentication.identityprovider.IdentityProviders;
import com.authentication.identityprovider.internal.entities.Account;
import com.authentication.identityprovider.internal.entities.AccountPermission;
import com.authentication.identityprovider.internal.entities.AuthPermission;
import com.authentication.identityprovider.internal.entities.AuthRolePermission;
import com.authentication.identityprovider.internal.model.PasswordInput;
import com.authentication.identityprovider.internal.model.ResetPasswordInput;
import com.authentication.identityprovider.internal.repository.AccountRepository;
import com.authentication.identityprovider.internal.service.AccountService;
import com.authentication.oauth2.OAuth2Constants;
import com.authentication.oauth2.PrivateClaims;
import com.authentication.request.AuthRequest;
import com.authentication.request.TokenRequest;
import com.authentication.response.AccessToken;
import com.internationalization.Messages;
import com.util.date.DateUtil;
import com.util.enums.HTTPCustomStatus;
import com.util.exceptions.ApiException;
import com.util.exceptions.ServiceException;
import com.util.password.PasswordException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.security.GeneralSecurityException;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class AuthenticationService {

    private final AuthenticationProvider accountService;
    private final TokenService tokenService;
    private final AccountRepository accountRepository;

    @Autowired
    public AuthenticationService(AccountService accountService, TokenService tokenService,
                                 AccountRepository accountRepository) {
        this.accountService = accountService;
        this.tokenService = tokenService;
        this.accountRepository = accountRepository;
    }

    @Transactional
    public AccessToken authenticate(TokenRequest authRequest, AccessChannel accessChannel, Locale locale) throws ApiException {
        AccessToken accessTokenResponse;
        switch (accessChannel) {
            case PASSWORD:
                accessTokenResponse = loginWithPassword((AuthRequest) authRequest, locale);
                break;
            case OTP:
                accessTokenResponse = otpLogin((AuthRequest) authRequest, locale);
                break;
            default:
                throw new ServiceException(ErrorCode.UNDEFINED_ACCESS_CHANNEL);
        }

        return accessTokenResponse;
    }

    private AccessToken loginWithPassword(AuthRequest authRequest, Locale locale) throws ApiException {
        if (StringUtils.isEmpty(authRequest.getEmail())) {
            throw new ApiException(Messages.get("USERNAME.MANDATORY", locale), HTTPCustomStatus.INVALID_REQUEST);
        }
        if (StringUtils.isEmpty(authRequest.getPassword())) {
            throw new ApiException(Messages.get("PASSWORD.MANDATORY", locale), HTTPCustomStatus.INVALID_REQUEST);
        }

        Account account = accountService.authenticate(authRequest, locale);

        List<String> roles = null;
        if (account.getRoles() != null) {
            roles = account.getRoles()
                    .stream()
                    .map(accountRole -> accountRole.getAuthRole().getName())
                    .limit(1)
                    .collect(Collectors.toList());
        }
        if(roles == null)
            roles = Collections.singletonList("visitor");

        Map<String, String> privateClaimMap =
                privateClaims(account.getEmail(), account.isActive(), roles);

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expirationTime = now.plus(30, ChronoUnit.DAYS);

        List<AuthPermission> permissions = account.getPermissions()
                .stream()
                .map(AccountPermission::getAuthPermission)
                .collect(Collectors.toList());

        List<AuthPermission> rolePermissions = account.getRoles()
                .stream()
                .flatMap(accountRole -> accountRole.getAuthRole().getRolePermissionList().stream())
                .map(AuthRolePermission::getPermission)
                .collect(Collectors.toList());
        permissions.addAll(rolePermissions);

        return getAccessToken(now, expirationTime, privateClaimMap, permissionsClaim(permissions), account.isActive(), account.getEmail());
    }


    private static Map<String, String> privateClaims(String email, boolean active, List<String> roles) {
        Map<String, String> privateClaimMap = new HashMap<>();
        if (email != null) {
            privateClaimMap.put(PrivateClaims.MAIL.getType(), email);
        }

        if (roles != null && !roles.isEmpty()) {
            privateClaimMap.put(PrivateClaims.ROLES.getType(), String.join(",", roles));
        }

        privateClaimMap.put(PrivateClaims.ACTIVE.getType(), String.valueOf(active));
        privateClaimMap.put(PrivateClaims.IDENTITY_PROVIDER.getType(), IdentityProviders.ING_PROJECT);

        return privateClaimMap;
    }

    private static Map<String, String[]> permissionsClaim(List<AuthPermission> permissions) {
        Map<String, String[]> privateClaimListMap = new HashMap<>();
        List<String> stringPermissions = permissions.stream()
                .map(permission -> permission.getAction() + ":" + permission.getSubject())
                .collect(Collectors.toList());
        stringPermissions.addAll(getAccountDefaultPermissions());

        privateClaimListMap.put(PrivateClaims.PERMISSIONS.getType(), stringPermissions.stream().distinct().toArray(String[]::new));
        return privateClaimListMap;
    }

    private static List<String> getAccountDefaultPermissions() {
        return Arrays.asList("read:user");
    }

    private AccessToken getAccessToken(LocalDateTime now, LocalDateTime expirationTime, Map<String, String> privateClaimMap, Map<String, String[]> privateClaimTypeAndStringArray, boolean active, String email) {
        long expiresIn = ChronoUnit.MINUTES.between(now, expirationTime);
        return AccessToken.builder()
                .accessToken(tokenService.generateJwtToken(expiresIn, privateClaimMap, privateClaimTypeAndStringArray))
                .tokenType(OAuth2Constants.BEARER_TYPE)
                .active(active)
                .refreshToken(tokenService.getRefreshToken(email))
//                .expireAt(DateUtil.convertToDateViaSqlDate(expirationTime.toLocalDate()))
                .expiresIn(Period.between(now.toLocalDate(), expirationTime.toLocalDate()).getDays())
                .expiresIn(ChronoUnit.SECONDS.between(now, expirationTime))
                .build();
    }


    @Transactional
    public Serializable setPassword(PasswordInput passwordInput, Locale locale) throws GeneralSecurityException, ApiException, PasswordException {
        return accountService.setPassword(passwordInput, locale);
    }

    @Transactional
    public Serializable resetPassword(ResetPasswordInput resetPasswordInput, Locale locale) throws ApiException, GeneralSecurityException {
        return accountService.resetPassword(resetPasswordInput, locale);
    }

    private AccessToken otpLogin(AuthRequest authRequest, Locale locale) throws ApiException {

        Account account = accountRepository.findByEmail(authRequest.getEmail()).orElseThrow(() ->
                new ApiException(Messages.get("USER.NOT.EXIST", locale), HTTPCustomStatus.UNAUTHORIZED)
        );


        List<String> roles = null;
        if (account.getRoles() != null) {
            roles = account.getRoles()
                    .stream()
                    .map(accountRole -> accountRole.getAuthRole().getName())
                    .limit(1)
                    .collect(Collectors.toList());
        }
        if(roles == null)
            roles = Collections.singletonList("visitor");

        Map<String, String> privateClaimMap =
                privateClaims(account.getEmail(), account.isActive(), roles);

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expirationTime = now.plus(30, ChronoUnit.DAYS);

        List<AuthPermission> permissions = account.getPermissions()
                .stream()
                .map(AccountPermission::getAuthPermission)
                .collect(Collectors.toList());

        List<AuthPermission> rolePermissions = account.getRoles()
                .stream()
                .flatMap(accountRole -> accountRole.getAuthRole().getRolePermissionList().stream())
                .map(AuthRolePermission::getPermission)
                .collect(Collectors.toList());
        permissions.addAll(rolePermissions);

        return getAccessToken(now, expirationTime, privateClaimMap, permissionsClaim(permissions), account.isActive(), account.getEmail());
    }

    @Transactional
    public Serializable generateOtp(String email, Locale locale) throws ApiException {
        return accountService.generateOtp(email, locale);
    }

}
