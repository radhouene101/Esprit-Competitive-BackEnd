package com.apex.picloud.services;

import com.apex.picloud.models.ProductCategory;
import com.apex.picloud.repositories.ProductCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.List;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

    @Autowired
    private ProductCategoryRepository categoryRepository;

    @Override
    public List<ProductCategory> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Optional<ProductCategory> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    @Override
    public ProductCategory createCategory(ProductCategory category) {
        System.out.println("Category received before saving: " + category); // ADD THIS LINE

        return categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    // Optional implementation for specific needs:
    // public List<ProductCategory> findByNameContaining(String name);
}