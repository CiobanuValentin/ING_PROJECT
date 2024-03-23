package com.api.mapper;

import com.api.entities.Product;
import com.api.model.ProductInput;
import com.api.output.ProductJSON;

public class ProductMapper {

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
                .build();
    }

    public static Product updateProductFromInput(Product product, ProductInput input) {
        product.setName(input.getName());
        product.setPrice(input.getPrice());
        product.setDescription(input.getDescription());

        return product;
    }
}
