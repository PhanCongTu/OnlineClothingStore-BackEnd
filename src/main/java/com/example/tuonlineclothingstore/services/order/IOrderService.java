package com.example.tuonlineclothingstore.services.order;

import com.example.tuonlineclothingstore.dtos.OrderDto;

import java.util.List;

public interface IOrderService {
    List<OrderDto> getAllOrder(Long userId);

    OrderDto newOrder(OrderDto orderDto);
}
