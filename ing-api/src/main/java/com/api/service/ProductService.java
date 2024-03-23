package com.api.service;

import com.api.entities.Product;
import com.api.model.ProductInput;
import com.api.output.ProductJSON;
import com.api.mapper.ProductMapper;
import com.api.repository.ProductRepository;
import com.util.exceptions.ApiException;
import com.internationalization.Messages;
import com.util.enums.HTTPCustomStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public ProductJSON saveOrUpdateProduct(ProductInput input) {
        Product product = ProductMapper.inputToProduct(input);
        product.setId(input.getId()); // if it is 0 it adds, if it is an existing id, it updates
        Product savedProduct = productRepository.save(product);
        return ProductMapper.productToJson(savedProduct);
    }

    @Transactional
    public ProductJSON addProduct(ProductInput input) {
        Product product = ProductMapper.inputToProduct(input);
        Product savedProduct = productRepository.save(product);
        return ProductMapper.productToJson(savedProduct);
    }

    @Transactional
    public ProductJSON findProduct(Integer productId, Locale locale) throws ApiException {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        Product product = optionalProduct.orElseThrow(() -> new ApiException(Messages.get("PRODUCT.NOT.EXIST", locale), HTTPCustomStatus.NOT_FOUND));
        return ProductMapper.productToJson(product);
    }

    @Transactional
    public ProductJSON updateProduct(Integer productId, ProductInput input, Locale locale) throws ApiException {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        Product product = optionalProduct.orElseThrow(() -> new ApiException(Messages.get("PRODUCT.NOT.EXIST", locale), HTTPCustomStatus.NOT_FOUND));

        Product updatedProduct = ProductMapper.updateProductFromInput(product, input);
        updatedProduct = productRepository.save(updatedProduct);

        return ProductMapper.productToJson(updatedProduct);
    }

    @Transactional
    public void deleteProduct(Integer productId) {
        productRepository.deleteById(productId);
    }

    @Transactional
    public List<ProductJSON> getAllProducts() {
        return productRepository.searchAll("");
    }

    @Transactional
    public List<ProductJSON> searchProducts(String input) {
        return productRepository.searchAll(input);
    }
}
