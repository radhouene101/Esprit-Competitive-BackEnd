package com.apex.picloud.services;

import com.apex.picloud.models.CartItem;

import java.util.List;

public interface CartService {
    CartItem addItemToCart(Long productId, double quantity);

    void deleteItemFromCart(Long cartItemId);
}
