package com.api.controller;

import com.api.config.Anonymous;
import com.api.model.ProductInput;
import com.api.model.ProductInput;
import com.api.output.Response;
import com.api.output.ProductJSON;
import com.api.output.ProductJSON;
import com.api.service.ProductService;
import com.api.service.ProductService;
import com.exception.ExceptionHandler;
import com.internationalization.Messages;
import com.token.validation.auth.AuthUtils;
import com.util.async.Computation;
import com.util.async.ExecutorsProvider;
import com.util.enums.HTTPCustomStatus;
import com.util.exceptions.ApiException;
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

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.Serial;
import java.io.Serializable;
import java.security.GeneralSecurityException;
import java.util.Locale;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutorService;


@Tag(description = "Product API", name = "Product Services")
@RequestMapping("/v1/")
@RestController
@Validated
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProductController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

    private final ProductService productService;

    private final SmartLocaleResolver smartLocaleResolver;

    @GetMapping(value = "product/{id}", produces = {"application/json"})
    @Anonymous
    @Operation(summary = "Load product",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Return product if it was successfully found",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ProductJSON.class)))
            })
//    @RolesAllowed({"visitor", "administrator"})
//    @PreAuthorize("hasAnyRole(@privilegeService.getPrivilegeRoles(\"LOAD.USER\")) AND hasAnyAuthority('PERMISSION_read:product', 'PERMISSION_edit:product')")
    public ResponseEntity<Serializable> load(@RequestHeader(name = "Authorization", required = false) String authorization,
                                             @PathParam("id") Integer id,
                                             HttpServletRequest request) {
        ExecutorService executorService = ExecutorsProvider.getExecutorService();
        return Computation.computeAsync(() -> loadProduct(id, smartLocaleResolver.resolveLocale(request)), executorService)
                .thenApplyAsync(Response::ok, executorService)
                .exceptionally(error -> ExceptionHandler.handleException((CompletionException) error))
                .join();
    }

    private Serializable loadProduct(Integer id, Locale language) throws ApiException {
        return productService.findProduct(id, language);
    }

    @GetMapping(value = "products", produces = {"application/json"})
    @Anonymous
    @Operation(summary = "Load all products",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Return the list of all products",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ProductJSON.class)))
            })
//    @RolesAllowed({"visitor", "administrator"})
//    @PreAuthorize("hasAnyRole(@privilegeService.getPrivilegeRoles(\"LOAD.USER\")) AND hasAnyAuthority('PERMISSION_read:product', 'PERMISSION_edit:product')")
    public ResponseEntity<Serializable> loadAll(@RequestHeader(name = "Authorization", required = false) String authorization,
                                             HttpServletRequest request) {
        ExecutorService executorService = ExecutorsProvider.getExecutorService();
        return Computation.computeAsync(() -> (Serializable)productService.getAllProducts(), executorService)
                .thenApplyAsync(Response::ok, executorService)
                .exceptionally(error -> ExceptionHandler.handleException((CompletionException) error))
                .join();
    }

    @GetMapping(value = "products/{input}", produces = {"application/json"})
    @Anonymous
    @Operation(summary = "Search products",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Return the list of all products containing the given input",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ProductJSON.class)))
            })
//    @RolesAllowed({"visitor", "administrator"})
//    @PreAuthorize("hasAnyRole(@privilegeService.getPrivilegeRoles(\"LOAD.USER\")) AND hasAnyAuthority('PERMISSION_read:product', 'PERMISSION_edit:product')")
    public ResponseEntity<Serializable> searchAll(@RequestHeader(name = "Authorization", required = false) String authorization,
                                                  @PathParam("input") String input,
                                                  HttpServletRequest request) {
        ExecutorService executorService = ExecutorsProvider.getExecutorService();
        return Computation.computeAsync(() -> (Serializable)productService.searchProducts(input), executorService)
                .thenApplyAsync(Response::ok, executorService)
                .exceptionally(error -> ExceptionHandler.handleException((CompletionException) error))
                .join();
    }

    @PostMapping(value = "product", consumes = {"application/json"}, produces = {"application/json"})
    @Operation(summary = "Create product",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Return product if successfully added",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ProductJSON.class)))
            })
    @Anonymous
    public ResponseEntity<Serializable> createProduct(@RequestBody @Valid ProductInput productInput, HttpServletRequest request) throws GeneralSecurityException {

        ExecutorService executorService = ExecutorsProvider.getExecutorService();
        return Computation.computeAsync(() -> (Serializable)productService.addProduct(productInput), executorService)
                .thenApplyAsync(Response::created, executorService)
                .exceptionally(error -> ExceptionHandler.handleException((CompletionException) error))
                .join();
    }

    @PutMapping(value = "product/{id}", consumes = {"application/json"}, produces = {"application/json"})
    @Operation(summary = "Update product",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Return the product if successfully updated",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ProductJSON.class)))
            })
    @Anonymous
    public ResponseEntity<Serializable> updateProduct(@RequestBody @Valid ProductInput productInput,
                                                      @PathParam("id") int id,
                                                      HttpServletRequest request) throws GeneralSecurityException {

        ExecutorService executorService = ExecutorsProvider.getExecutorService();
        return Computation.computeAsync(() -> (Serializable)productService.updateProduct(id, productInput, smartLocaleResolver.resolveLocale(request)), executorService)
                .thenApplyAsync(Response::created, executorService)
                .exceptionally(error -> ExceptionHandler.handleException((CompletionException) error))
                .join();
    }

    @DeleteMapping(value = "product/{id}", consumes = {"application/json"}, produces = {"application/json"})
    @Operation(summary = "Delete product",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Return json with status ok if successfully deleted",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ProductJSON.class)))
            })
    @Anonymous
    public ResponseEntity<Serializable> deleteProduct(@PathParam("id") int id,
                                                      HttpServletRequest request) throws GeneralSecurityException {

        ExecutorService executorService = ExecutorsProvider.getExecutorService();
        return Computation.computeAsync(() -> (Serializable)productService.deleteProduct(id), executorService)
                .thenApplyAsync(Response::created, executorService)
                .exceptionally(error -> ExceptionHandler.handleException((CompletionException) error))
                .join();
    }

}
