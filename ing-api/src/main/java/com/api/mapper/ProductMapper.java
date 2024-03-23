package com.api.mapper;

import com.api.entities.Product;
import com.api.model.ProductInput;
import com.api.output.ProductJSON;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

public class ProductMapper {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static Product inputToProduct(ProductInput input) {
        return Product.builder()
                .name(input.getName())
                .price(input.getPrice())
                .description(input.getDescription())
                .build();
    }

    public static ProductJSON productToJson(Product product) {
        return ProductJSON.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .description(product.getDescription())
//                .createdAt(product.getCreatedAt())
//                .modifiedAt(product.getModifiedAt())
                .createdAt(formatOffsetDateTime(product.getCreatedAt()))
                .modifiedAt(formatOffsetDateTime(product.getModifiedAt()))
                .build();
    }

    public static Product updateProductFromInput(Product product, ProductInput input) {
        product.setName(input.getName());
        product.setPrice(input.getPrice());
        product.setDescription(input.getDescription());

        return product;
    }

    private static String formatOffsetDateTime(OffsetDateTime offsetDateTime) {
        return offsetDateTime != null ? offsetDateTime.format(formatter) : null;
    }
}
