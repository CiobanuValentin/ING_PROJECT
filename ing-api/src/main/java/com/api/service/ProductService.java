package com.api.service;

import com.api.entities.Product;
import com.api.model.ProductInput;
import com.api.output.ProductJSON;
import com.api.mapper.ProductMapper;
import com.api.repository.ProductRepository;
import com.util.exceptions.ApiException;
import com.internationalization.Messages;
import com.util.enums.HTTPCustomStatus;
import com.util.web.JsonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static com.api.config.ClockConfig.UTC_CLOCK;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProductService {

    private final ProductRepository productRepository;

    @Qualifier(UTC_CLOCK)
    private final Clock clock;

    @Transactional
    public ProductJSON addProduct(ProductInput input) {
        final OffsetDateTime now = OffsetDateTime.now(clock);
        Product product = ProductMapper.inputToProduct(input);
        product.setCreatedAt(now);
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
    public JsonResponse deleteProduct(Integer productId, Locale locale) {
        if(!productRepository.existsById(productId))
            throw new ApiException(Messages.get("PRODUCT.NOT.EXIST", locale), HTTPCustomStatus.NOT_FOUND);
        productRepository.deleteById(productId);
        return new JsonResponse()
                .with("Status", "ok")
                .with("message", "Product was successfully deleted!")
                .done();
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
