package com.example.tuonlineclothingstore.services.cart;

import com.example.tuonlineclothingstore.dtos.CartDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ICartService {
    List<CartDto> getAllCartByUserId(Long userId);

    Page<CartDto> getAllCartByUserId(Long userId, int page, int size, String sort, String column);

    CartDto getCartById(Long cartId);

    CartDto addOrUpdateCart(CartDto cartDto, Long userId);


    int countAllCartByUserId(Long userId);

    void deleteCart(Long cartId);
}
