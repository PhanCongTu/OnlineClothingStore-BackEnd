package com.example.tuonlineclothingstore.dtos.User;

import com.example.tuonlineclothingstore.utils.EnumRole;
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
public class UserDto {

    private long id;

    private String name;

    private String userName;

    private String avatar;

    private String phoneNumber;

    private String email;

    private Boolean isActive;

    private Date createAt;

    private Date updateAt;

    private List<String> roles;
}
