package com.example.tuonlineclothingstore.services.cart;

import com.example.tuonlineclothingstore.dtos.CartDto;
import com.example.tuonlineclothingstore.entities.Cart;
import com.example.tuonlineclothingstore.entities.User;
import com.example.tuonlineclothingstore.exceptions.NotFoundException;
import com.example.tuonlineclothingstore.repositories.CartRepository;
import com.example.tuonlineclothingstore.services.product.IProductService;
import com.example.tuonlineclothingstore.services.user.IUserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CartServiceImpl implements ICartService {
    private final CartRepository cartRepository;
    private final IUserService iUserService;
    private final IProductService iProductService;
    private ModelMapper modelMapper;

    @Override
    public List<CartDto> getAllCartByUserId(Long userId) {
        List<Cart> carts = cartRepository.findAllByUserId(userId);
        return carts.stream()
                .map((cart) -> modelMapper.map(cart, CartDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public CartDto getCartById(Long cartId) {
        Cart cart = cartRepository.findById(cartId).orElse(null);
        if (cart==null)
            throw new NotFoundException("Cant find category!");
        return modelMapper.map(cart, CartDto.class);
    }

    @Override
    public CartDto addOrUpdateCart(CartDto cartDto, Long userId){
        List<CartDto> carts = getAllCartByUserId(userId);
        int plus = cartDto.getQuantity();
        for (CartDto cart: carts
             ) {
            if (cart.getProduct().getId() == cartDto.getProduct().getId()){
                cartDto = cart ;
                cartDto.setQuantity(cart.getQuantity() + plus);
//                Cart newCart = modelMapper.map(cart, Cart.class);
//                newCart.setUser(modelMapper.map(iUserService.getUserById(userId), User.class));
//                cartRepository.save(modelMapper.map(newCart, Cart.class));
                break;
            }
        }
    Cart cart = modelMapper.map(cartDto, Cart.class);
    cart.setUser(modelMapper.map(iUserService.getUserById(userId), User.class));
    CartDto savedCardDto = modelMapper.map(cartRepository.save(cart), CartDto.class);
        return savedCardDto;
    }

    @Override
    public int countAllCartByUserId(Long userId){
        return cartRepository.countByUserId(userId);
    }

    @Override
    public void deleteCart(Long cartId){
        cartRepository.deleteById(cartId);
    }
}
