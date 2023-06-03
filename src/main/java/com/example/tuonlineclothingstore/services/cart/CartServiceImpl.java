package com.example.tuonlineclothingstore.services.cart;

import com.example.tuonlineclothingstore.dtos.CartDto;
import com.example.tuonlineclothingstore.entities.Cart;
import com.example.tuonlineclothingstore.entities.User;
import com.example.tuonlineclothingstore.exceptions.NotFoundException;
import com.example.tuonlineclothingstore.repositories.CartRepository;
import com.example.tuonlineclothingstore.services.product.IProductService;
import com.example.tuonlineclothingstore.services.user.IUserService;
import com.example.tuonlineclothingstore.utils.PageUtils;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public Page<CartDto> getAllCartByUserId(Long userId, int page, int size, String sort, String column) {
        Pageable pageable = PageUtils.createPageable(page, size, sort, column);
        Page<Cart> carts = cartRepository.findAllByUserId(userId,pageable);
        return carts.map(cart -> modelMapper.map(cart, CartDto.class));
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
            if (cart.getProduct().getId() == cartDto.getProduct().getId() && cart.getSize().trim().equals(cartDto.getSize().trim())){
                cartDto = cart ;
                cartDto.setQuantity(cart.getQuantity() + plus);
                break;
            }
        }
    Cart cart = modelMapper.map(cartDto, Cart.class);
    cart.setUser(modelMapper.map(iUserService.getUserById(userId), User.class));
        return modelMapper.map(cartRepository.save(cart), CartDto.class);
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
