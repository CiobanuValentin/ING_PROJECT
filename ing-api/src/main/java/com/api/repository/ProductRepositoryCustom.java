package com.api.repository;


import com.api.output.ProductJSON;

import java.util.List;

public interface ProductRepositoryCustom {
    List<ProductJSON> searchAll(String input);
}
