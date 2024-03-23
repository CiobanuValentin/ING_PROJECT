package com.api.controller;

import com.api.model.PermissionInput;
import com.api.output.PermissionJSON;
import com.api.output.Response;
import com.api.service.PermissionService;
import com.exception.ExceptionHandler;
import com.util.async.Computation;
import com.util.async.ExecutorsProvider;
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


@Tag(description = "Permission API", name = "Permission Services")
@RequestMapping("/v1/")
@RestController
@Validated
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PermissionController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PermissionController.class);

    private final PermissionService permissionService;


    @GetMapping(value = "permissions", produces = {"application/json"})
    @PreAuthorize("hasRole('administrator')")
    @Operation(summary = "Load all permissions",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Return the list of all permissions",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = PermissionJSON.class)))
            })
    public ResponseEntity<Serializable> loadAll(@RequestHeader(name = "Authorization", required = false) String authorization,
                                             HttpServletRequest request) {
        ExecutorService executorService = ExecutorsProvider.getExecutorService();
        return Computation.computeAsync(() -> (Serializable)permissionService.getAllPermissions(), executorService)
                .thenApplyAsync(Response::ok, executorService)
                .exceptionally(error -> ExceptionHandler.handleException((CompletionException) error))
                .join();
    }

    @PostMapping(value = "permission", consumes = {"application/json"}, produces = {"application/json"})
    @Operation(summary = "Create permission",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Return permission if successfully added",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = PermissionJSON.class)))
            })
    @PreAuthorize("hasRole('administrator')")
    public ResponseEntity<Serializable> createPermission(@RequestBody @Valid PermissionInput permissionInput, HttpServletRequest request) throws GeneralSecurityException {

        ExecutorService executorService = ExecutorsProvider.getExecutorService();
        return Computation.computeAsync(() -> (Serializable)permissionService.addPermission(permissionInput), executorService)
                .thenApplyAsync(Response::created, executorService)
                .exceptionally(error -> ExceptionHandler.handleException((CompletionException) error))
                .join();
    }


}
