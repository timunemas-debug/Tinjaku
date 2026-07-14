package com.tinjaku.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.tinjaku.dto.request.OnlineRequest;
import com.tinjaku.dto.request.UserRequest;
import com.tinjaku.dto.response.OnlineResponse;
import com.tinjaku.dto.response.UserResponse;
import com.tinjaku.exception.BadRequestException;
import com.tinjaku.exception.ResourceNotFound;
import com.tinjaku.mapper.UserMapper;
import com.tinjaku.model.StatusOnOff;
import com.tinjaku.model.User;
import com.tinjaku.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    
    @Mock
    UserRepository userRepository;

    @Mock
    UserMapper userMapper;

    @InjectMocks
    UserService userService;

    @Test
    public void shouldTambahUser(){

        User user = new User();
        user.setNamaDepan("Jeremy");
        user.setNamaBelakang("Darma");

        UserRequest request = new UserRequest();
        request.setNamaDepan("Jeremy");
        request.setNamaBelakang("Darma");

        UserResponse response = new UserResponse();
        response.setNamaDepan("Jeremy");
        response.setNamaBelakang("Darma");

        when(userRepository.existsByNamaDepanIgnoreCaseAndNamaBelakangIgnoreCase("Jeremy", "Darma"))
                .thenReturn(false);
        
        when(userRepository.save(user))
                .thenReturn(user);
        
        when(userMapper.toEntity(request))
                .thenReturn(user);

        when(userMapper.toResponse(user))
                .thenReturn(response);
        
        UserResponse result = userService.tambahUser(request);

        assertEquals("Jeremy", result.getNamaDepan());
        assertEquals("Darma", result.getNamaBelakang());

        verify(userRepository).existsByNamaDepanIgnoreCaseAndNamaBelakangIgnoreCase("Jeremy", "Darma");
        verify(userRepository).save(user);
        verify(userMapper).toEntity(request);
        verify(userMapper).toResponse(user);
    }

    @Test
    public void shouldThrowBadRequestWhenUserAlreadyExists(){

        UserRequest request = new UserRequest();
        request.setNamaDepan("Jeremy");
        request.setNamaBelakang("Darma");

        when(userRepository.existsByNamaDepanIgnoreCaseAndNamaBelakangIgnoreCase("Jeremy", "Darma"))
                .thenReturn(true);
        
        assertThrows(BadRequestException.class, () -> userService.tambahUser(request));

        verify(userRepository).existsByNamaDepanIgnoreCaseAndNamaBelakangIgnoreCase("Jeremy", "Darma");
        verify(userMapper, never()).toEntity(any());
        verify(userMapper, never()).toResponse(any());
    }

    @Test
    public void shouldGetAllUser(){

        User user1 = new User();
        user1.setNamaDepan("Jeremy");
        user1.setNamaBelakang("Darma");

        User user2 = new User();
        user2.setNamaDepan("Pretty");
        user2.setNamaBelakang("Renanda");

        UserResponse userResponse1 = new UserResponse();
        userResponse1.setNamaDepan("Jeremy");
        userResponse1.setNamaBelakang("Darma");

        UserResponse userResponse2 = new UserResponse();
        userResponse2.setNamaDepan("Pretty");
        userResponse2.setNamaBelakang("Renanda");

        when(userRepository.findAll())
                .thenReturn(List.of(user1, user2));
            
        when(userMapper.toResponse(user1))
                .thenReturn(userResponse1);

        when(userMapper.toResponse(user2))
                .thenReturn(userResponse2);

        List<UserResponse> result = userService.getAllUser();

        assertEquals(2, result.size());
        assertEquals("Jeremy", result.get(0).getNamaDepan());
        assertEquals("Pretty", result.get(1).getNamaDepan());

        verify(userRepository).findAll();
        verify(userMapper).toResponse(user1);
        verify(userMapper).toResponse(user2);
    }

    @Test
    public void shouldGetUserById(){

        User user = new User();
        user.setUserId(1L);

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));

        User result = userService.getUserById(1L);

        assertEquals(1L, result.getUserId());

        verify(userRepository).findById(1L);
    }

    @Test
    public void shouldThrowResourceNotFoundWhenUserDoesNotExist(){

        when(userRepository.findById(1L))
                .thenReturn(Optional.empty());
        
        assertThrows(ResourceNotFound.class, () -> userService.getUserById(1L));

        verify(userRepository).findById(1L);
    }

    @Test
    public void shouldGetUserResponseById(){

        User user = new User();
        user.setUserId(1L);

        UserResponse response = new UserResponse();
        response.setNamaDepan("Jeremy");
        response.setNamaBelakang("Darma");
        response.setNamaLengkap("Jeremy Putra Darma");

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));
        
        when(userMapper.toResponse(user))
                .thenReturn(response);

        UserResponse result = userService.getUserResponseById(1L);

        assertEquals("Jeremy", result.getNamaDepan());
        assertEquals("Darma", result.getNamaBelakang());
        assertEquals("Jeremy Putra Darma", result.getNamaLengkap());

        verify(userRepository).findById(1L);
        verify(userMapper).toResponse(user);
    }

    @Test
    public void shouldDeleteUserById(){

        when(userRepository.existsById(1L))
                .thenReturn(true);

        userService.deleteUserById(1L);

        verify(userRepository).existsById(1L);
        verify(userRepository).deleteById(1L);
    }

    @Test
    public void shouldDeleteUserByIdResourceNotFound(){

        when(userRepository.existsById(1L))
                .thenReturn(false);

        assertThrows(ResourceNotFound.class, () -> userService.deleteUserById(1L));

        verify(userRepository).existsById(1L);
        verify(userRepository, never()).deleteById(any());
    }

    @Test
    public void shouldUpdateUserStatusToOnline(){

        User user = new User();
        user.setUserId(1L);

        OnlineRequest request = new OnlineRequest();
        request.setStatusOnOff(StatusOnOff.ONLINE);

        OnlineResponse response = new OnlineResponse();
        response.setStatusOnOff(StatusOnOff.ONLINE);

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));

        when(userRepository.save(user))
                .thenReturn(user);

        when(userMapper.toOnlineResponse(user))
                .thenReturn(response);

        OnlineResponse result = userService.getUserOnline(1L, request);

        assertEquals(StatusOnOff.ONLINE, result.getStatusOnOff());

        verify(userRepository).save(user);
        verify(userMapper).toOnlineResponse(user);
    }
}