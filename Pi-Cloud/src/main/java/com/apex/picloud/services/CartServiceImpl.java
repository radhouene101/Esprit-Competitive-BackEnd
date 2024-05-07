package com.apex.picloud.services;

import com.apex.picloud.models.CartItem;
import com.apex.picloud.models.MarketplaceProduct;
import com.apex.picloud.repositories.CartItemRepository;
import com.apex.picloud.repositories.MarketplaceProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private MarketplaceProductRepository productRepository;

    @Override
    public CartItem addItemToCart(Long productId, double quantity) {
        // Retrieve product from the database
        MarketplaceProduct product = productRepository.findById(productId)
                .orElseThrow(() -> new MarketplaceProductServiceImpl.ResourceNotFoundException("Product with id " + productId + " not found"));

        // Check if the product already exists in the cart
        List<CartItem> existingCartItems = cartItemRepository.findByProduct_Id(productId);

        if (!existingCartItems.isEmpty()) {
            // Product already exists in the cart, update the quantity
            CartItem existingCartItem = existingCartItems.get(0); // Assuming there's only one item for a product in the cart
            existingCartItem.setQuantity(existingCartItem.getQuantity() + quantity);
            existingCartItem.setPrice(existingCartItem.getPrice() + (product.getProductPrice() * quantity)); // Update the price
            return cartItemRepository.save(existingCartItem);
        } else {
            // Create a new CartItem
            CartItem cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setPrice(product.getProductPrice() * quantity); // Set the price
            return cartItemRepository.save(cartItem);
        }
    }


    @Override
    public void deleteItemFromCart(Long cartItemId) {
        cartItemRepository.deleteById(cartItemId);
    }
}
