package com.api.controller;

import com.api.config.Anonymous;
import com.api.model.AssignationInput;
import com.api.model.RoleInput;
import com.api.output.RoleJSON;
import com.api.output.Response;
import com.api.service.RoleService;
import com.exception.ExceptionHandler;
import com.util.async.Computation;
import com.util.async.ExecutorsProvider;
import com.util.web.SmartLocaleResolver;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.Serializable;
import java.security.GeneralSecurityException;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutorService;


@Tag(description = "Role API", name = "Role Services")
@RequestMapping("/v1/")
@RestController
@Validated
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RoleController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoleController.class);

    private final RoleService roleService;

    private final SmartLocaleResolver smartLocaleResolver;


    @GetMapping(value = "roles", produces = {"application/json"})
    @PreAuthorize("hasRole('administrator')")
    @Operation(summary = "Load all roles",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Return the list of all roles",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = RoleJSON.class)))
            })
    public ResponseEntity<Serializable> loadAll(@RequestHeader(name = "Authorization", required = false) String authorization,
                                             HttpServletRequest request) {
        ExecutorService executorService = ExecutorsProvider.getExecutorService();
        return Computation.computeAsync(() -> (Serializable)roleService.getAllRoles(), executorService)
                .thenApplyAsync(Response::ok, executorService)
                .exceptionally(error -> ExceptionHandler.handleException((CompletionException) error))
                .join();
    }

//    @PostMapping(value = "role", consumes = {"application/json"}, produces = {"application/json"})
//    @Operation(summary = "Create role",
//            responses = {
//                    @ApiResponse(responseCode = "200", description = "Return role if successfully added",
//                            content = @Content(mediaType = "application/json",
//                                    schema = @Schema(implementation = RoleJSON.class)))
//            })
//    @PreAuthorize("hasRole('administrator')")
//    public ResponseEntity<Serializable> createRole(@RequestBody @Valid RoleInput roleInput, HttpServletRequest request) throws GeneralSecurityException {
//
//        ExecutorService executorService = ExecutorsProvider.getExecutorService();
//        return Computation.computeAsync(() -> (Serializable)roleService.addRole(roleInput), executorService)
//                .thenApplyAsync(Response::created, executorService)
//                .exceptionally(error -> ExceptionHandler.handleException((CompletionException) error))
//                .join();
//    }

    @PostMapping(value = "role/permission/assign", consumes = {"application/json"}, produces = {"application/json"})
    @Operation(summary = "Create role",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Return json with ok if successfully assigned permission to role",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = RoleJSON.class)))
            })
    @PreAuthorize("hasRole('administrator')")
    public ResponseEntity<Serializable> assignPermission(@RequestBody @Valid AssignationInput assignationInput, HttpServletRequest request) throws GeneralSecurityException {

        ExecutorService executorService = ExecutorsProvider.getExecutorService();
        return Computation.computeAsync(() -> (Serializable)roleService.assignPermission(assignationInput,  smartLocaleResolver.resolveLocale(request)), executorService)
                .thenApplyAsync(Response::created, executorService)
                .exceptionally(error -> ExceptionHandler.handleException((CompletionException) error))
                .join();
    }


}
