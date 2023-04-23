package com.example.tuonlineclothingstore.controllers;

import com.example.tuonlineclothingstore.dtos.CartDto;
import com.example.tuonlineclothingstore.dtos.Order.AddOrderDto;
import com.example.tuonlineclothingstore.dtos.Order.OrderDto;
import com.example.tuonlineclothingstore.dtos.OrderItemDto;
import com.example.tuonlineclothingstore.dtos.User.UserDto;
import com.example.tuonlineclothingstore.entities.Order;
import com.example.tuonlineclothingstore.entities.OrderItem;
import com.example.tuonlineclothingstore.entities.Product;
import com.example.tuonlineclothingstore.services.cart.ICartService;
import com.example.tuonlineclothingstore.services.orderitem.IOrderItemService;
import com.example.tuonlineclothingstore.services.order.IOrderService;
import com.example.tuonlineclothingstore.services.user.IUserService;
import com.example.tuonlineclothingstore.utils.EnumOrderStatus;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/order")
@CrossOrigin("*")
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

    /***
     *  Controller dành cho admin để xem order của user
     * @param userId : ID của user
     * @return : Danh sách các order
     */
    @GetMapping("/user/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<OrderDto>> getAllOrder(@PathVariable Long userId) {
        List<OrderDto> orderDtos = iOrderService.getAllOrder(userId);
        return new ResponseEntity<>(orderDtos, HttpStatus.OK);
    }

    /***
     *  Controller dành cho user để lấy order của mình
     * @param principal : lấy từ token
     * @return : Danh sách các order
     */
    @GetMapping("/my-order")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<OrderDto>> getMyOrder(Principal principal) {
        UserDto userDto = iUserService.getUserByUserName(principal.getName());
        System.out.println(userDto);
        List<OrderDto> orderDtos = iOrderService.getAllOrder(userDto.getId());
        return new ResponseEntity<>(orderDtos, HttpStatus.OK);
    }

    /***]
     * @author : Tu
     * @param principal : lấy từ token (Chỉ user mới được đặt hàng)
     * @param addOrderDto : Truyền vào thông tin cần thiết
     * @return : Thông tin về order mơi
     */
    @PostMapping("/add")
    public ResponseEntity<OrderDto> addToOrder(Principal principal,
                                               @RequestBody AddOrderDto addOrderDto) {

         String address = addOrderDto.getAddress();

         String phoneNumber =addOrderDto.getPhoneNumber();

         String note =addOrderDto.getNote();

         List<Long> idCarts = addOrderDto.getIdCarts();

        UserDto loginedUser = iUserService.getUserByUserName(principal.getName());
        List<CartDto> cartsWantToBuy = new ArrayList<>();
        
        double totalMoney = 0;


        if (idCarts.isEmpty()) {
            System.out.println("List empty");
            cartsWantToBuy = iCartService.getAllCartByUserId(loginedUser.getId());
        } else {
            System.out.println("List" + idCarts);
            for (Long id : idCarts
            ) {
                CartDto cartDto = iCartService.getCartById(id);
                if (cartDto != null) {
                    cartsWantToBuy.add(cartDto);
                }
            }
        }

        for (CartDto cartDto: cartsWantToBuy) {
            totalMoney += cartDto.getProduct().getPrice()*cartDto.getQuantity();
        }


        OrderDto orderDto = new OrderDto();
        orderDto.setUser(loginedUser);
        orderDto.setTotal(totalMoney);
        orderDto.setAddress(address);
        orderDto.setPhoneNumber(phoneNumber);
        orderDto.setStatus(EnumOrderStatus.CHO_XAC_NHAN.name());
        orderDto.setNote(note);
        orderDto.setCreateAt(new Date(new java.util.Date().getTime()));
        OrderDto newOrder = iOrderService.newOrder(orderDto);

        List<OrderItemDto> orderItemDtos = new ArrayList<>();
        for (CartDto cartDto : cartsWantToBuy
        ) {
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
