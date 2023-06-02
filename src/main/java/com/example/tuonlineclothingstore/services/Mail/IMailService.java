package com.example.tuonlineclothingstore.services.Mail;

import com.example.tuonlineclothingstore.Model.Mail;
import com.example.tuonlineclothingstore.dtos.Order.OrderDto;
import com.example.tuonlineclothingstore.dtos.OrderItemDto;
import com.example.tuonlineclothingstore.dtos.User.UserDto;
import org.springframework.scheduling.annotation.Async;

import java.util.List;

public interface IMailService {
    public void sendEmail(Mail mail);

    @Async
    void sendOrderMail(UserDto userDto, OrderDto orderDto, List<OrderItemDto> orderItemDtos);

    @Async
    void sendUpdateStatusOrderMail(OrderDto orderDto);

    @Async
    void sendCodeForgetPassword(String email, String resetCode);
}
