package com.example.tuonlineclothingstore.services.orderitem;

import com.example.tuonlineclothingstore.dtos.OrderItemDto;
import com.example.tuonlineclothingstore.entities.OrderItem;

import java.util.List;

public interface IOrderItemService {
    List<OrderItemDto> getALlOrderItemByOrderId(Long orderId);


    OrderItemDto addOrderItem(OrderItem orderItem);
}
