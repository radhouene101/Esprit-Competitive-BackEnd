package com.apex.picloud.repositories;

import com.apex.picloud.models.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {
    // You can define custom query methods if needed
    ProductCategory save(ProductCategory post);
}
