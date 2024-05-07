package com.apex.picloud.controllers;

import com.apex.picloud.models.MarketplaceProduct;
import com.apex.picloud.models.ProductCategory;
import com.apex.picloud.services.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;
import java.util.List;

@RestController
@RequestMapping("/api/marketplace/categories")
public class ProductCategoryController {

    @Autowired
    private ProductCategoryService categoryService;

    @GetMapping("/getAllCategories")
    public ResponseEntity<List<ProductCategory>> getAllCategories() {
        List<ProductCategory> categories = categoryService.getAllCategories();
        System.out.println("Categories fetched from service: " + categories); // ADD THIS LINE

        return ResponseEntity.ok(categories);
    }

    @GetMapping("/getCategoryById/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable Long id) {
        // Retrieve the category from the service layer
        return categoryService.getCategoryById(id)
                .map(category -> ResponseEntity.ok(category)) // Map the optional to ResponseEntity.ok if present
                .orElse(ResponseEntity.notFound().build()); // Return ResponseEntity.notFound() if not present
    }

    @PostMapping("/addCategory")
    public ProductCategory createProduct(@RequestBody ProductCategory category) {
        return categoryService.createCategory(category);
    }

    @DeleteMapping("/deleteCategory/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}

//ken delete tmchi