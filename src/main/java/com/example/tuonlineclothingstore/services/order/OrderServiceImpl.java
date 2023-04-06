package com.example.tuonlineclothingstore.services.order;

import com.example.tuonlineclothingstore.dtos.OrderDto;
import com.example.tuonlineclothingstore.entities.Order;
import com.example.tuonlineclothingstore.repositories.OrderRepository;
import com.example.tuonlineclothingstore.services.cart.ICartService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements IOrderService {
    private final OrderRepository orderRepository;
    private final ICartService iCartService;
    private ModelMapper modelMapper;

    @Override
    public List<OrderDto> getAllOrder(Long userId){
        List<Order> orders = orderRepository.findAllByUserId(userId);
        return orders.stream()
                .map((order) -> (modelMapper.map(order, OrderDto.class)))
                .collect(Collectors.toList());
    }

    @Override
    public OrderDto newOrder(OrderDto orderDto){
        Order order = modelMapper.map(orderDto, Order.class);
        Order savedOrder = orderRepository.save(order);
        OrderDto saveCategoryDto = modelMapper.map(savedOrder, OrderDto.class);
        return saveCategoryDto;

    }
}