package com.example.tuonlineclothingstore.services.orderitem;

import com.example.tuonlineclothingstore.dtos.OrderItemDto;
import com.example.tuonlineclothingstore.entities.OrderItem;
import com.example.tuonlineclothingstore.repositories.OrderItemRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderItemServiceImpl implements IOrderItemService {
    private OrderItemRepository orderItemRepository;
    private ModelMapper modelMapper;

    @Override
    public List<OrderItemDto> getALlOrderItemByOrderId(Long orderId){
        List<OrderItem> orderItems = orderItemRepository.findAllByOrderId(orderId);
        return orderItems.stream()
                .map((oderItem) -> modelMapper.map(oderItem, OrderItemDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public OrderItemDto addOrderItem(OrderItem orderItem){
//        OrderItem orderItem = modelMapper.map(orderItemDto, OrderItem.class);
        OrderItem savedCategory = orderItemRepository.save(orderItem);
        OrderItemDto saveOrderItemDto = modelMapper.map(savedCategory, OrderItemDto.class);
        return saveOrderItemDto;
    }
}
