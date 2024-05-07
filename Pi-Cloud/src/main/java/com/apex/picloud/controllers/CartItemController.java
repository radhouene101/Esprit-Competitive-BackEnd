package com.apex.picloud.controllers;

import com.apex.picloud.models.CartItem;
import com.apex.picloud.services.CartItemService;
import com.apex.picloud.services.MarketplaceProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart-items")
public class CartItemController {

    @Autowired
    private CartItemService cartItemService;

    @GetMapping
    public List<CartItem> getAllItems() {
        return cartItemService.getAllItems();
    }

    @GetMapping("/{id}")
    public CartItem getItemById(@PathVariable Long id) {
        return cartItemService.getItemById(id)
                .orElseThrow(() -> new MarketplaceProductServiceImpl.ResourceNotFoundException("Cart item with id " + id + " not found"));
    }

    @PostMapping
    public CartItem addItem(@RequestBody CartItem item) {
        return cartItemService.addItem(item);
    }

    @PutMapping("/{id}")
    public CartItem updateItem(@PathVariable Long id, @RequestBody CartItem itemDetails) {
        return cartItemService.updateItem(id, itemDetails);
    }

    @DeleteMapping("/{id}")
    public void deleteItem(@PathVariable Long id) {
        cartItemService.deleteItem(id);
    }



    @GetMapping("/findByProductId/{productId}")
    public ResponseEntity<List<CartItem>> findByProductId(@PathVariable Long productId) {
        List<CartItem> cartItems = cartItemService.findByProduct_Id(productId);
        return ResponseEntity.ok(cartItems);
    }

}
