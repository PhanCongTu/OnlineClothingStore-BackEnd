package com.example.tuonlineclothingstore.services.cart;

import com.example.tuonlineclothingstore.dtos.CartDto;

import java.util.List;

public interface ICartService {
    List<CartDto> getAllCartByUserId(Long userId);


    CartDto getCartById(Long cartId);

    CartDto addOrUpdateCart(CartDto cartDto, Long userId);


    int countAllCartByUserId(Long userId);

    void deleteCart(Long cartId);
}
