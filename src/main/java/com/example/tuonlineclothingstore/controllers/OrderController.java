package com.example.tuonlineclothingstore.controllers;

import com.example.tuonlineclothingstore.dtos.CartDto;
import com.example.tuonlineclothingstore.dtos.OrderDto;
import com.example.tuonlineclothingstore.dtos.OrderItemDto;
import com.example.tuonlineclothingstore.dtos.User.UserDto;
import com.example.tuonlineclothingstore.entities.Order;
import com.example.tuonlineclothingstore.entities.OrderItem;
import com.example.tuonlineclothingstore.entities.Product;
import com.example.tuonlineclothingstore.services.cart.ICartService;
import com.example.tuonlineclothingstore.services.orderitem.IOrderItemService;
import com.example.tuonlineclothingstore.services.order.IOrderService;
import com.example.tuonlineclothingstore.services.user.IUserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/Api/Order")
public class OrderController {

    @Autowired
    IOrderService iOrderService;

    @Autowired
    ICartService iCartService;

    @Autowired
    IUserService iUserService;

    @Autowired
    IOrderItemService iOrderItemService;

    final ModelMapper modelMapper;

    public OrderController(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @GetMapping("/user")
    public ResponseEntity<List<OrderDto>> getAllOrder(@RequestParam Long userId) {
        List<OrderDto> orderDtos = iOrderService.getAllOrder(userId);
        return new ResponseEntity<>(orderDtos, HttpStatus.OK);
    }

    /***]
     * @author : Tu
     * @param userId : Truyền vào id của người đặt
     * @param address : Truyền vào địa chỉ
     * @param phoneNumber: Truyền vào số điện thoại
     * @param idCarts: Truyền vào list<Long> id của các cart muốn order
     *               , nếu k truyền thì mặc định là order tất cả trong cart
     * @return : Thông tin về order mơi
     */
    @PostMapping("/add")
    public ResponseEntity<OrderDto> addToOrder(@RequestParam("userId") Long userId,
                                               @RequestParam("address") String address,
                                               @RequestParam("phoneNumber") String phoneNumber,
                                               @RequestParam(value = "idCarts" ,required = false, defaultValue = "") List<Long> idCarts ) {
        OrderDto orderDto = new OrderDto();
        double total = 0;
        List<CartDto> cartDtos = new ArrayList<>();
        if (idCarts.isEmpty()) {
            System.out.println("List empty");
            cartDtos = iCartService.getAllCartByUserId(userId);
        } else {
            System.out.println("List" + idCarts);
            for (Long id : idCarts
            ) {
                CartDto cartDto = iCartService.getCartById(id);
                if (cartDto != null) {
                    cartDtos.add(cartDto);
                }
            }
        }
        for (CartDto cartDto: cartDtos
             ) {
            total += cartDto.getProduct().getPrice()*cartDto.getQuantity();
        }

        UserDto userDto = iUserService.getUserById(userId);
        List<OrderItemDto> orderItemDtos = new ArrayList<>();


        orderDto.setUser(userDto);
        orderDto.setTotal(total);
        orderDto.setAddress(address);
        orderDto.setPhoneNumber(phoneNumber);


        OrderDto newOrder = iOrderService.newOrder(orderDto);

        for (CartDto cartDto : cartDtos
        ) {
            total += cartDto.getQuantity() * cartDto.getProduct().getPrice();
            OrderItem newOne = modelMapper.map(cartDto, OrderItem.class);
            newOne.setOrder(modelMapper.map(newOrder, Order.class));
            newOne.setProduct(modelMapper.map(cartDto.getProduct(), Product.class));
            OrderItemDto newNewOne = iOrderItemService.addOrderItem(newOne);
            orderItemDtos.add(newNewOne);
            iCartService.deleteCart(cartDto.getId());
        }


        newOrder.setOrderItems(orderItemDtos);
        return new ResponseEntity<>(newOrder, HttpStatus.OK);
    }

}
