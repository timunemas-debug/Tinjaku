package com.tinjaku.service;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import com.tinjaku.dto.request.OnlineRequest;
import com.tinjaku.dto.response.OnlineResponse;
import com.tinjaku.exception.ResourceNotFound;
import com.tinjaku.mapper.UserMapper;
import com.tinjaku.model.StatusOnOff;
import com.tinjaku.model.User;
import com.tinjaku.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    
    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    
    @Test
    public void shouldReturnUser(){

        User user = new User();
        user.setUserId(1L);
        
        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));

        User result = userService.getUserById(1L);

        assertEquals(1L, result.getUserId());
    }

    @Test
    public void shouldThrowResourceNotFoundWhenUserNotFound(){
        
        when(userRepository.findById(1L))
                .thenReturn(Optional.empty());
        
        assertThrows(ResourceNotFound.class, () -> {
            userService.getUserById(1L);
        });
    }

    @Test
    public void shouldDeleteUserSuccessfully(){

        // Arange
        User user = new User();
        user.setUserId(1L);

        when(userRepository.existsById(1L))
                .thenReturn(true);

        userService.deleteUserById(1L);

        verify(userRepository).deleteById(1L);
    }

    @Test
    public void shouldUserOnlineSuccessfully(){

        User user = new User();
        user.setUserId(1L);

        OnlineRequest request = new OnlineRequest();
        request.setStatusOnOff(StatusOnOff.ONLINE);

        OnlineResponse response = new OnlineResponse();

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));
        
        when(userRepository.save(user))
                .thenReturn(user);

        when(userMapper.toOnlineResponse(user))
                .thenReturn(response);

        OnlineResponse result = userService.getUserOnline(1L, request);
        
        verify(userRepository).save(user);
        verify(userMapper).toOnlineResponse(user);

        assertEquals(response, result);
        assertEquals(StatusOnOff.ONLINE, user.getStatusOnOff());
    }
}
