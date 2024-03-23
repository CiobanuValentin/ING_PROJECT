package com.api.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.api.entities.Product;
import com.api.model.ProductInput;
import com.api.output.ProductJSON;
import com.api.repository.ProductRepository;
import com.util.exceptions.ApiException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import java.time.Clock;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

//@SpringBootTest(classes = {com.api.service.ProductService.class})
class ProductServiceTest {
//
//    @Mock
//    private ProductRepository productRepository;
//
//    @Mock
//    private Clock clock;
//
//    private ProductService productService;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.initMocks(this);
//        productService = new ProductService(productRepository, clock);
//    }
//
//    @Test
//    void testAddProduct() {
//        ProductInput input = new ProductInput();
//        input.setName("Test Product");
//        input.setPrice(10.0);
//        input.setDescription("Test Description");
//
//        OffsetDateTime now = OffsetDateTime.now();
//        when(clock.instant()).thenReturn(now.toInstant());
//        when(clock.getZone()).thenReturn(now.getOffset());
//
//        Product product = new Product();
//        product.setId(1);
//        product.setName("Test Product");
//        product.setPrice(10.0);
//        product.setDescription("Test Description");
//        product.setCreatedAt(now);
//
//        when(productRepository.save(any(Product.class))).thenReturn(product);
//
//        ProductJSON result = productService.addProduct(input);
//
//        assertNotNull(result);
//        assertEquals(product.getName(), result.getName());
//        assertEquals(product.getPrice(), result.getPrice());
//        assertEquals(product.getDescription(), result.getDescription());
//    }
//
//    @Test
//    void testFindProduct() throws ApiException {
//        Integer productId = 1;
//        Locale locale = Locale.ENGLISH;
//
//        Product product = new Product();
//        product.setId(productId);
//        product.setName("Test Product");
//
//        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
//
//        ProductJSON result = productService.findProduct(productId, locale);
//
//        assertNotNull(result);
//        assertEquals(product.getName(), result.getName());
//    }

}
