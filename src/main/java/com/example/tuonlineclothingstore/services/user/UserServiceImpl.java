package com.example.tuonlineclothingstore.services.user;

import com.example.tuonlineclothingstore.dtos.SignUp;
import com.example.tuonlineclothingstore.dtos.User.ChangePasswordDto;
import com.example.tuonlineclothingstore.dtos.User.UpdateUserDto;
import com.example.tuonlineclothingstore.dtos.User.UserDto;
import com.example.tuonlineclothingstore.entities.User;
import com.example.tuonlineclothingstore.exceptions.DuplicateKeyException;
import com.example.tuonlineclothingstore.exceptions.InvalidException;
import com.example.tuonlineclothingstore.exceptions.NotFoundException;
import com.example.tuonlineclothingstore.repositories.UserRepository;
import com.example.tuonlineclothingstore.utils.EnumRole;
import com.example.tuonlineclothingstore.utils.PageUtils;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.*;

@Service
@AllArgsConstructor
public class UserServiceImpl implements IUserService {
    private final UserRepository userRepository;
    private ModelMapper modelMapper;

    @Override
    public Page<UserDto> filter(String search, int page, int size,
                                String sort, String column) {
        Pageable pageable = PageUtils.createPageable(page, size, sort, column);
        Page<User> users = userRepository.findByNameContainingAndEmailContainingAllIgnoreCase(search, search, pageable);
        return users.map(user -> modelMapper.map(user, UserDto.class));
    }

    @Override
    public UserDto getUserById(Long UserId) {
        Optional<User> UserOp = userRepository.findById(UserId);
        if (!UserOp.isPresent())
            throw new NotFoundException("Cant find User!");
        return modelMapper.map(UserOp.get(), UserDto.class);
    }

    @Override
    public Boolean checkExistByUsername(String username) {
        {
            return userRepository.existsByUserName(username);
        }
    }

    @Override
    public UserDto createUser(SignUp signUp) {
        User User = modelMapper.map(signUp, User.class);
        if (userRepository.existsByUserName(signUp.getUserName().trim()))
            throw new InvalidException("Username existed !!");
        if (userRepository.existsByEmail(signUp.getEmail().trim())) throw new InvalidException("Email existed !!");
        User.setRoles(Arrays.asList(EnumRole.ROLE_USER.name()));
        User.setIsActive(true);
        return modelMapper.map(userRepository.save(User), UserDto.class);
    }

    // Cập nhật lại User (cập nhật lại toàn bộ các thuộc tính)
    @Override
    public UserDto updateUser(Principal principal, UpdateUserDto userDto) {
        User existingUser = userRepository.findByUserName(principal.getName());
        if (existingUser == null) throw new NotFoundException("Unable to update User!");
        // Nếu mã không giống mã cũ thì kiểm tra mã mới đã tồn tại trong database hay chưa
        if (!existingUser.getEmail().equals(userDto.getEmail())
                && userRepository.existsByEmail(userDto.getEmail())) {
            throw new DuplicateKeyException(String.format("Email đã %s đã tồn tại", userDto.getEmail()));
        }
//        modelMapper.map(userDto, existingUser);
        existingUser.setName(userDto.getName());
        existingUser.setEmail(userDto.getEmail());
        existingUser.setPhoneNumber(userDto.getPhoneNumber());
        existingUser.setAvatar(userDto.getAvatar());
        existingUser.setUpdateAt(new Date(new java.util.Date().getTime()));
        return modelMapper.map(userRepository.save(existingUser), UserDto.class);
    }

    @Override
    public void changeStatusUser(Long UserId) {
        Optional<User> existingUser = userRepository.findById(UserId);
        if (!existingUser.isPresent()) throw new NotFoundException("Unable to dalete User!");

        existingUser.get().setIsActive(!existingUser.get().getIsActive());
        existingUser.get().setUpdateAt(new Date(new java.util.Date().getTime()));
        userRepository.save(existingUser.get());
    }

    @Override
    public UserDto getUserByUserNameAndPassword(String userName, String password) {
        User user = userRepository.findByUserNameAndPassword(userName, password);
        if (user == null) throw new NotFoundException("Wrong username or password !!");
        if (!user.getIsActive()) throw new NotFoundException("User has been deleted !");
        return modelMapper.map(user, UserDto.class);
    }
    @Override
    public UserDto getUserByUserName(String userName) {
        User user = userRepository.findByUserName(userName);
        if (user == null) throw new NotFoundException("Wrong username or password !!");
        if (!user.getIsActive()) throw new NotFoundException("User has been deleted !");
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public void deleteUser(Long userId) {
        Optional<User> existingUser = userRepository.findById(userId);
        if (!existingUser.isPresent()) throw new NotFoundException("Unable to dalete User!");
        userRepository.deleteById(userId);
    }

    @Override
    public void changePassword(ChangePasswordDto userDto, Principal principal) {
        User user = userRepository.findByUserName(principal.getName());
        if (!user.getPassword().trim().equals(userDto.getOldPassword()))
            throw new InvalidException("Password Invalid");
        user.setPassword(userDto.getNewPassword());
        userRepository.save(user);
    }

    @Override
    public void upgradeRole(Long userId) {
        Optional<User> existingUser = userRepository.findById(userId);
        if (!existingUser.isPresent()) throw new NotFoundException("User not found!");
        existingUser.get().getRoles().add(EnumRole.ROLE_ADMIN.name());
        userRepository.save(existingUser.get());
    }
    @Override
    public UserDto getMyInf(Principal principal){
        return modelMapper.map(userRepository.findByUserName(principal.getName()), UserDto.class);
    }
}
