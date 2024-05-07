package com.apex.picloud.services;

import com.apex.picloud.models.CartItem;
import com.apex.picloud.repositories.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartItemServiceImpl implements CartItemService {

    @Autowired
    private CartItemRepository cartItemRepository;

    @Override
    public List<CartItem> getAllItems() {
        return cartItemRepository.findAll();
    }

    @Override
    public Optional<CartItem> getItemById(Long id) {
        return cartItemRepository.findById(id);
    }

    @Override
    public CartItem addItem(CartItem item) {
        return cartItemRepository.save(item);
    }

    @Override
    public CartItem updateItem(Long id, CartItem itemDetails) {
        Optional<CartItem> itemOptional = cartItemRepository.findById(id);


            CartItem item = itemOptional.get();
            item.setProduct(itemDetails.getProduct());
            item.setQuantity(itemDetails.getQuantity());
            item.setPrice(itemDetails.getPrice());
            return cartItemRepository.save(item);

    }

    @Override
    public void deleteItem(Long id) {
        cartItemRepository.deleteById(id);
    }




    @Override
    public List<CartItem> findByProduct_Id(Long productId) {
        return cartItemRepository.findByProduct_Id(productId);
    }

}
