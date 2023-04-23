package com.example.tuonlineclothingstore.dtos.Order;

import com.example.tuonlineclothingstore.dtos.OrderItemDto;
import com.example.tuonlineclothingstore.dtos.User.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    private long id;

    private String address;

    private String phoneNumber;

    private String note;

    private String status;

    private double total;

    private Date createAt;

    private UserDto user;

    private List<OrderItemDto> orderItems;


}
