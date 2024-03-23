package com.api.service;


import com.api.config.SecurityFilter;
import com.api.entities.Product;
import com.api.model.ProductInput;
import com.api.output.ProductJSON;
import com.api.repository.ProductRepository;
import com.api.service.util.TestEntityGenerator;
import com.token.validation.jwt.JwtClaims;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.*;
import java.time.Clock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private Clock clock;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void addProductTest() {
        Clock fixedClock = Clock.fixed(Instant.parse("2024-03-25T12:00:00Z"), ZoneId.systemDefault());
        when(clock.instant()).thenReturn(fixedClock.instant());
        when(clock.getZone()).thenReturn(fixedClock.getZone());

        ProductInput input = TestEntityGenerator.generateProductInput();

        Product savedProduct = TestEntityGenerator.generateProductEntity();
        when(productRepository.save(any(Product.class))).thenReturn(savedProduct);

        ProductJSON expectedProductJSON = TestEntityGenerator.generateProductJSON();
        ProductJSON actualProductJSON = productService.addProduct(input);

        assertEquals(expectedProductJSON.getId(), actualProductJSON.getId());
        assertEquals(expectedProductJSON.getName(), actualProductJSON.getName());
        assertEquals(expectedProductJSON.getPrice(), actualProductJSON.getPrice());
        assertEquals(expectedProductJSON.getDescription(), actualProductJSON.getDescription());
        assertEquals(expectedProductJSON.getCreatedAt(), actualProductJSON.getCreatedAt());
        assertEquals(expectedProductJSON.getModifiedAt(), actualProductJSON.getModifiedAt());

        verify(productRepository, times(1)).save(any(Product.class));
    }


    @Test
    void findProductTest() {
        int productId = 1;
        Locale locale = Locale.US;

        Product product = TestEntityGenerator.generateProductEntity();
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        ProductJSON expectedProductJSON = TestEntityGenerator.generateProductJSON();
        ProductJSON actualProductJSON = productService.findProduct(productId, locale);

        assertEquals(expectedProductJSON.getId(), actualProductJSON.getId());
        assertEquals(expectedProductJSON.getName(), actualProductJSON.getName());
        assertEquals(expectedProductJSON.getPrice(), actualProductJSON.getPrice());
        assertEquals(expectedProductJSON.getDescription(), actualProductJSON.getDescription());
        assertEquals(expectedProductJSON.getCreatedAt(), actualProductJSON.getCreatedAt());
        assertEquals(expectedProductJSON.getModifiedAt(), actualProductJSON.getModifiedAt());

    }


}
