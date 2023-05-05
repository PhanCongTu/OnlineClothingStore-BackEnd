package com.example.tuonlineclothingstore.services.user;

import com.example.tuonlineclothingstore.dtos.SignUp;
import com.example.tuonlineclothingstore.dtos.User.ChangePasswordDto;
import com.example.tuonlineclothingstore.dtos.User.UpdateUserDto;
import com.example.tuonlineclothingstore.dtos.User.UserDto;
import com.example.tuonlineclothingstore.dtos.UserPagination;
import com.example.tuonlineclothingstore.entities.User;
import org.springframework.data.domain.Page;

import java.security.Principal;
import java.util.List;
import java.util.Map;

public interface IUserService {
    Page<UserDto> filter(String search, int page, int size,
                      String sort, String column);

    UserDto getUserById(Long UserId);

    Boolean checkExistByUsername(String username);

    UserDto createUser(SignUp signUp);

    // Cập nhật lại User (cập nhật lại toàn bộ các thuộc tính)
    UserDto updateUser(Principal principal, UpdateUserDto UserDto);

    void changeStatusUser(Long UserId);

    UserDto getUserByUserNameAndPassword(String userName, String password);

    UserDto getUserByUserName(String userName);

    void deleteUser(Long userId);

    void changePassword(ChangePasswordDto userDto, Principal principal);

    void upgradeRole(Long userId);

    UserDto getMyInf(Principal principal);

    boolean resetPassword(String username, String newPass);
}
