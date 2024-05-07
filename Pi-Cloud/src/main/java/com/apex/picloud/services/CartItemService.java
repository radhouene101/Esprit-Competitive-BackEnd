package com.apex.picloud.services;

import com.apex.picloud.models.CartItem;

import java.util.List;
import java.util.Optional;

public interface CartItemService {
    List<CartItem> getAllItems();

    Optional<CartItem> getItemById(Long id);

    CartItem addItem(CartItem item);

    CartItem updateItem(Long id, CartItem itemDetails);

    void deleteItem(Long id);


    List<CartItem> findByProduct_Id(Long productId);

}
