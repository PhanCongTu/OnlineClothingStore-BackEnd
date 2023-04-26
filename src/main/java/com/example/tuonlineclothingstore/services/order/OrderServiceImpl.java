package com.example.tuonlineclothingstore.services.order;

import com.example.tuonlineclothingstore.dtos.Order.OrderDto;
import com.example.tuonlineclothingstore.dtos.User.UserDto;
import com.example.tuonlineclothingstore.entities.Order;
import com.example.tuonlineclothingstore.entities.Product;
import com.example.tuonlineclothingstore.entities.User;
import com.example.tuonlineclothingstore.exceptions.NotFoundException;
import com.example.tuonlineclothingstore.repositories.OrderRepository;
import com.example.tuonlineclothingstore.services.cart.ICartService;
import com.example.tuonlineclothingstore.services.user.IUserService;
import com.example.tuonlineclothingstore.utils.PageUtils;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements IOrderService {
    private final OrderRepository orderRepository;
    private final IUserService iUserService;
    private ModelMapper modelMapper;
    @Override
    public Page<OrderDto> filter(String searchText, String status, int page, int size, String sort, String column){
        Pageable pageable = PageUtils.createPageable(page, size, sort, column);
        Page<Order> orders = orderRepository.findAllByAddressContainingAndStatusContainingOrPhoneNumberContainingAndStatusContainingAllIgnoreCase(searchText, status, searchText, status,pageable);
    return  orders.map(order -> modelMapper.map(order, OrderDto.class));



    }
    @Override
    public List<OrderDto> getAllOrder(Long userId){
        List<Order> orders = orderRepository.findAllByUserIdOrderByCreateAtDesc(userId);
        List<OrderDto> orderDtos = new ArrayList<>();
        for (Order oder: orders
             ) {
            orderDtos.add(modelMapper.map(oder, OrderDto.class));
        }
        return orderDtos;
    }

    @Override
    public OrderDto newOrder(OrderDto orderDto){
        Order order = modelMapper.map(orderDto, Order.class);
        Order savedOrder = orderRepository.save(order);
        OrderDto saveCategoryDto = modelMapper.map(savedOrder, OrderDto.class);
        return saveCategoryDto;

    }
    @Override
    public  OrderDto updateStatus(Long orderId, String status){
        Order order = orderRepository.findById(orderId).orElse(null);
        if (order==null){
            throw new NotFoundException("Không tìm thấy order có ID là: " + orderId);
        }
        order.setStatus(status);
        Order newOrder = orderRepository.save(order);
        return modelMapper.map(newOrder, OrderDto.class);
    }
    @Override
    public OrderDto getOrder(Long orderId){
        Order order = orderRepository.findById(orderId).orElse(null);
        if(order==null) throw new NotFoundException("Không tìm thấy Order");
        return modelMapper.map(order, OrderDto.class);
    }
}
