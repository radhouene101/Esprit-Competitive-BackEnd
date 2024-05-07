package com.apex.picloud.services;

import com.apex.picloud.models.MarketplaceProduct;
import com.apex.picloud.models.ProductCategory;

import java.util.List;
import java.util.Optional;

public interface MarketplaceProductService {
    List<MarketplaceProduct> getAllProducts();
    Optional<MarketplaceProduct> getProductById(Long id);
    MarketplaceProduct createProduct(MarketplaceProduct product);
    void deleteProduct(Long id);
    MarketplaceProduct updateProduct(Long id, MarketplaceProduct productDetails);
    List<MarketplaceProduct> findProductsByCategory(ProductCategory category);
}
