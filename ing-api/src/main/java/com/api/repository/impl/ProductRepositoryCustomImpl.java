package com.api.repository.impl;

import com.api.entities.Product;
import com.api.output.ProductJSON;
import com.api.repository.ProductRepositoryCustom;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class ProductRepositoryCustomImpl implements ProductRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    //criteria api so it selects directly into json, skipping another iteration for mapping to json
    @Override
    public List<ProductJSON> searchAll(String input) {
        final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<ProductJSON> criteriaQuery = criteriaBuilder.createQuery(ProductJSON.class);
        final Root<Product> productRoot = criteriaQuery.from(Product.class);

        criteriaQuery.select(
                criteriaBuilder.construct(
                        ProductJSON.class,
                        productRoot.get("name")
                )
        ).where(
                criteriaBuilder.like(
                        criteriaBuilder.lower(productRoot.get("name")),
                        "%" + input.toLowerCase() + "%"
                )
        );


        return entityManager.createQuery(criteriaQuery).getResultList();
    }

}
