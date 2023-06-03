package com.example.tuonlineclothingstore.services.user;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.tuonlineclothingstore.dtos.User.UserDto;
import com.example.tuonlineclothingstore.entities.User;
import com.example.tuonlineclothingstore.repositories.UserRepository;
import com.example.tuonlineclothingstore.services.Mail.IMailService;

import java.time.LocalDate;
import java.time.ZoneOffset;

import java.util.ArrayList;
import java.util.Date;

import com.example.tuonlineclothingstore.utils.EnumRole;
import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {UserServiceImpl.class})
@ExtendWith(SpringExtension.class)
class UserServiceImplTest {

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserServiceImpl userServiceImpl;

    /**
     * Method under test: {@link UserServiceImpl#filter(String, int, int, String, String)}
     */
    @Test
    void testFilter() {
        when(userRepository.findByNameContainingOrEmailContainingOrPhoneNumberContainingAllIgnoreCase(
                any(),any(), any(), any()))
                .thenReturn(new PageImpl<>(new ArrayList<>()));
        assertTrue(userServiceImpl.filter("a", 1, 3, "asc", "name").toList().isEmpty());
        verify(userRepository).findByNameContainingOrEmailContainingOrPhoneNumberContainingAllIgnoreCase(
                any(), any(), any(), any());
    }

    /**
     * Method under test: {@link UserServiceImpl#filter(String, int, int, String, String)}
     */
    @Test
    void testFilter2() {
        User user = new User();
        user.setAvatar(null);
        user.setCarts(new ArrayList<>());
        user.setCreateAt(new Date());
        user.setEmail("user01@gmail.com");
        user.setId(1L);
        user.setIsActive(true);
        user.setName("user01");
        user.setOrders(new ArrayList<>());
        user.setPassword("123");
        user.setPhoneNumber("123123123");
        user.setUpdateAt(new Date());
        user.setUserName("user01");

        ArrayList<User> content = new ArrayList<>();
        content.add(user);
        PageImpl<User> pageImpl = new PageImpl<>(content);
        when(userRepository.findByNameContainingOrEmailContainingOrPhoneNumberContainingAllIgnoreCase(
                any(), any(), any(), any()))
                .thenReturn(pageImpl);
        // JUnit5
        //assertEquals(1, userServiceImpl.filter("a", 1, 3, "asc", "name").toList().size());

        // AssertJ
        assertThat(userServiceImpl.filter("a", 1, 3, "asc", "name").toList().size())
                .isEqualTo(1);
        verify(userRepository).findByNameContainingOrEmailContainingOrPhoneNumberContainingAllIgnoreCase(
                any(), any(), any(), any());
    }

    /**
     * Method under test: {@link UserServiceImpl#filter(String, int, int, String, String)}
     */
    @Test
    void testFilter3() {
        User user = new User();
        user.setAvatar(null);
        user.setCarts(new ArrayList<>());
        user.setCreateAt(new Date());
        user.setEmail("user01@gmail.com");
        user.setId(1L);
        user.setIsActive(true);
        user.setName("user01");
        user.setOrders(new ArrayList<>());
        user.setPassword("123");
        user.setPhoneNumber("123123");
        user.setUpdateAt(new Date());
        user.setUserName("user01");

        User user2 = new User();
        user2.setAvatar(null);
        user2.setCarts(new ArrayList<>());
        user2.setCreateAt(new Date());
        user2.setEmail("user02@gmail.com");
        user2.setId(2L);
        user2.setIsActive(false);
        user2.setName("user2");
        user2.setOrders(new ArrayList<>());
        user2.setPassword("123");
        user2.setPhoneNumber("123123");
        user2.setUpdateAt(new Date());
        user2.setUserName("user2");

        ArrayList<User> content = new ArrayList<>();
        content.add(user2);
        content.add(user);
        PageImpl<User> pageImpl = new PageImpl<>(content);
        when(userRepository.findByNameContainingOrEmailContainingOrPhoneNumberContainingAllIgnoreCase(
                any(), any(), any(), any()))
                .thenReturn(pageImpl);

//        assertEquals(2, userServiceImpl.filter("", 0, 10, "asc", "name").toList().size());
        assertThat(userServiceImpl.filter("aaaaa", 0, 10, "asc", "name").toList().size())
                .isEqualTo(2);

        verify(userRepository).findByNameContainingOrEmailContainingOrPhoneNumberContainingAllIgnoreCase(
                any(),any(), any(), any());

    }


}

