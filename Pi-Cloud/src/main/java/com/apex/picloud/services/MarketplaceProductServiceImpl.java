package com.apex.picloud.services;

import com.apex.picloud.models.MarketplaceProduct;
import com.apex.picloud.models.ProductCategory;
import com.apex.picloud.repositories.MarketplaceProductRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MarketplaceProductServiceImpl implements MarketplaceProductService {

    @Autowired
    private MarketplaceProductRepository productRepository;

    @Autowired
    private ProductCategoryService categoryService;

    @Override
    public List<MarketplaceProduct> findProductsByCategory(ProductCategory category) {
        return productRepository.findByCategory(category);
    }

    @Override
    public List<MarketplaceProduct> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Optional<MarketplaceProduct> getProductById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public MarketplaceProduct createProduct(MarketplaceProduct product) {
        // Retrieve the product category by ID using the category service
        Long categoryId = product.getCategory().getId(); // Assuming the category has an ID field
        Optional<ProductCategory> categoryOptional = categoryService.getCategoryById(categoryId);
//hell
        // Check if the category exists
        ProductCategory category = categoryOptional.orElseThrow(() -> new ResourceNotFoundException("Category with ID " + categoryId + " not found"));

        // Set the retrieved category to the product
        product.setCategory(category);

        // Save the product
        return productRepository.save(product);
    }


    @Override
    public MarketplaceProduct updateProduct(Long id, MarketplaceProduct productDetails) {
        // Retrieve the existing product by id
        Optional<MarketplaceProduct> productOptional = productRepository.findById(id);

        // Ensure the product exists before updating
        if (productOptional.isPresent()) {
            MarketplaceProduct product = productOptional.get();

            // Update the product's attributes with the provided details
            product.setProductName(productDetails.getProductName());
            product.setProductDescription(productDetails.getProductDescription());
            product.setProductPrice(productDetails.getProductPrice());

            product.setCategory(productDetails.getCategory());
            // ... update any other relevant fields (e.g., imageUrl)

            // Save the updated product using the repository
            return productRepository.save(product);
        } else {
            // Handle the case where the product is not found
            throw new ResourceNotFoundException("Product with id " + id + " not found");
        }
    }
    public static class ResourceNotFoundException extends RuntimeException {

        public ResourceNotFoundException(String message) {
            super(message);
        }
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }





}
