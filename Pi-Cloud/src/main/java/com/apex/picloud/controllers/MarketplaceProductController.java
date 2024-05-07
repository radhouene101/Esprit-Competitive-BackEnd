package com.apex.picloud.controllers;


import com.apex.picloud.models.CartItem;
import com.apex.picloud.repositories.CartItemRepository;
import org.springframework.http.MediaType;
import com.apex.picloud.models.MarketplaceProduct;
import com.apex.picloud.models.ProductCategory;
import com.apex.picloud.services.MarketplaceProductService;
import com.apex.picloud.services.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.Optional;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


import java.util.List;

@RestController
@RequestMapping("/api/marketplace/products")
public class MarketplaceProductController {

    @Autowired
    private MarketplaceProductService productService;
    @Autowired

    private ProductCategoryService productCategoryService;

    @Autowired
    private CartItemRepository cartItemRepository;




    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<MarketplaceProduct>> findProductsByCategoryId(@PathVariable Long categoryId) {
        Optional<ProductCategory> optionalCategory = productCategoryService.getCategoryById(categoryId);

        return optionalCategory.map(category -> {
                    List<MarketplaceProduct> products = productService.findProductsByCategory(category);
                    return ResponseEntity.ok(products);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @PostMapping(value = "/addProduct", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MarketplaceProduct> createProduct(
            @RequestParam("productName") String productName,
            @RequestParam("productDescription") String productDescription,
            @RequestParam("productPrice") double productPrice,
            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
            @RequestParam(value = "categoryId", required = false) Long categoryId) {

        // Create a new MarketplaceProduct object
        MarketplaceProduct product = new MarketplaceProduct();
        product.setProductName(productName);
        product.setProductDescription(productDescription);
        product.setProductPrice(productPrice);

        // Handle image information if provided
        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                String fileName = imageFile.getOriginalFilename();

                // Save the image to the database
                product.setImageUrl(fileName);

                // BADEL EL PATH FEL MERGE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                String angularAssetsPath = "C:\\Users\\oussama\\Desktop\\PI\\Frontend\\Angular-Chat-App-FrontEnd\\src\\assets\\images";
                Path imagePath = Paths.get(angularAssetsPath, fileName);
                Files.copy(imageFile.getInputStream(), imagePath);

            } catch (IOException e) {
                // Handle any exceptions during image saving
                e.printStackTrace();
            }
        }

        // Associate the product with a category (if provided)
        if (categoryId != null) {
            Optional<ProductCategory> optionalCategory = productCategoryService.getCategoryById(categoryId);
            if (optionalCategory.isPresent()) {
                product.setCategory(optionalCategory.get());
            }
        }

        // Save the product
        MarketplaceProduct savedProduct = productService.createProduct(product);
        return ResponseEntity.ok(savedProduct);
    }

    @GetMapping("/getProductById/{id}")
    public ResponseEntity<MarketplaceProduct> getProductById(@PathVariable Long id) {
        Optional<MarketplaceProduct> productOptional = productService.getProductById(id);
        if (productOptional.isPresent()) {
            MarketplaceProduct product = productOptional.get();
            return ResponseEntity.ok(product);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/getAllProducts")
    public ResponseEntity<List<MarketplaceProduct>> getAllProducts() {
        List<MarketplaceProduct> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @PutMapping("/updateProduct/{id}")
    public ResponseEntity<MarketplaceProduct> updateProduct(@PathVariable Long id, @RequestBody MarketplaceProduct productDetails) {
        MarketplaceProduct updatedProduct = productService.updateProduct(id, productDetails);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/deleteProduct/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        // Delete associated CartItems
        List<CartItem> cartItems = cartItemRepository.findByProduct_Id(id);
        cartItemRepository.deleteAll(cartItems);

        // Delete the product
        productService.deleteProduct(id);

        return ResponseEntity.noContent().build();
    }
}
