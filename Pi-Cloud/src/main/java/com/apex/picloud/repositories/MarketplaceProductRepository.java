package com.apex.picloud.repositories;

import com.apex.picloud.models.MarketplaceProduct;
import com.apex.picloud.models.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MarketplaceProductRepository extends JpaRepository<MarketplaceProduct, Long> {
    List<MarketplaceProduct> findByCategory(ProductCategory category);
    // You can define custom query methods if needed
   // MarketplaceProduct save(MarketplaceProduct post);
}
