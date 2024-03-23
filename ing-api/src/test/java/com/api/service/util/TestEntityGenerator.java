package com.api.service.util;

import com.api.entities.Product;
import com.api.model.ProductInput;
import com.api.output.ProductJSON;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

public class TestEntityGenerator {

    private static final OffsetDateTime CREATED_AT = OffsetDateTime.parse("2024-03-25T12:00:00Z");
    private static final OffsetDateTime MODIFIED_AT = OffsetDateTime.parse("2024-03-26T12:00:00Z");
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    public static ProductJSON generateProductJSON() {
        return ProductJSON.builder()
                .id(1)
                .name("Sample Product")
                .price(10.99)
                .description("Sample description")
                .createdAt(formatOffsetDateTime(CREATED_AT))
                .modifiedAt(formatOffsetDateTime(MODIFIED_AT))
                .build();
    }

    public static Product generateProductEntity() {
        return Product.builder()
                .id(1)
                .name("Sample Product")
                .price(10.99)
                .description("Sample description")
                .createdAt(CREATED_AT)
                .modifiedAt(MODIFIED_AT)
                .build();
    }

    public static Product generateProductEntity(OffsetDateTime createdAt, OffsetDateTime modifiedAt) {
        return Product.builder()
                .id(1)
                .name("Sample Product")
                .price(10.99)
                .description("Sample description")
                .createdAt(createdAt)
                .modifiedAt(modifiedAt)
                .build();
    }

    public static ProductInput generateProductInput() {
        return ProductInput.builder()
                .name("Sample Product")
                .price(10.99)
                .description("Sample description")
                .build();
    }

    private static String formatOffsetDateTime(OffsetDateTime offsetDateTime) {
        return offsetDateTime != null ? offsetDateTime.format(formatter) : null;
    }

}
