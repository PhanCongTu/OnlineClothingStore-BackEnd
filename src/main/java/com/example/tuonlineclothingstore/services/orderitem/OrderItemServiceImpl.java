package com.example.tuonlineclothingstore.services.orderitem;

import com.example.tuonlineclothingstore.dtos.OrderItemDto;
import com.example.tuonlineclothingstore.dtos.Product.ProductDto;
import com.example.tuonlineclothingstore.entities.OrderItem;
import com.example.tuonlineclothingstore.entities.Product;
import com.example.tuonlineclothingstore.exceptions.NotFoundException;
import com.example.tuonlineclothingstore.repositories.OrderItemRepository;
import com.example.tuonlineclothingstore.repositories.ProductRepository;
import com.example.tuonlineclothingstore.services.product.IProductService;
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
    private IProductService iProductService;
    private ProductRepository productRepository;

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
//        ProductDto productDto = iProductService.getProductById(orderItem.getProduct().getId());
        Product product = productRepository.findById(orderItem.getProduct().getId()).orElse(null);
        if (product == null) throw new NotFoundException("Không tìm thấy sản phẩm");
        product.setQuantity(product.getQuantity()-orderItem.getQuantity());
        product.setSold(product.getSold() + orderItem.getQuantity());
        productRepository.save(product);
        OrderItem savedCategory = orderItemRepository.save(orderItem);
        OrderItemDto saveOrderItemDto = modelMapper.map(savedCategory, OrderItemDto.class);
        return saveOrderItemDto;
    }
}
