package com.apex.picloud.services;
import com.apex.picloud.models.ProductCategory;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.List;
@Service
public interface ProductCategoryService {
    List<ProductCategory> getAllCategories();
    Optional<ProductCategory> getCategoryById(Long id); // Change return type to Optional
    ProductCategory createCategory(ProductCategory category);
    void deleteCategory(Long id);
    // Optional: Add methods for specific needs (e.g., findByName)
}