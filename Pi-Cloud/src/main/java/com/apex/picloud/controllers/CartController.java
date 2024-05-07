package com.apex.picloud.controllers;

import com.apex.picloud.models.CartItem;
import com.apex.picloud.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/add-item")
    public ResponseEntity<CartItem> addItemToCart(@RequestBody CartItem cartItem) {
        CartItem addedCartItem = cartService.addItemToCart(cartItem.getProduct().getId(), cartItem.getQuantity());
        return new ResponseEntity<>(addedCartItem, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete-item/{cartItemId}")
    public ResponseEntity<?> deleteItemFromCart(@PathVariable Long cartItemId) {
        cartService.deleteItemFromCart(cartItemId);
        return ResponseEntity.noContent().build();
    }

}
