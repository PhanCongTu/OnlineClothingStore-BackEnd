package com.example.tuonlineclothingstore.services.order;

import com.example.tuonlineclothingstore.dtos.Order.OrderDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IOrderService {
    List<OrderDto> getAllOrder(Long userId);

    OrderDto newOrder(OrderDto orderDto);

    Page<OrderDto> filter(String searchText, String status, int page, int size, String sort, String column);

    OrderDto updateStatus(Long orderId, String status);

    OrderDto getOrder(Long orderId);
}
