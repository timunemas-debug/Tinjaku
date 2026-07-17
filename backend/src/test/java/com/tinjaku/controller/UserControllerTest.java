package com.tinjaku.controller;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tinjaku.dto.request.OnlineRequest;
import com.tinjaku.dto.request.UserRequest;
import com.tinjaku.dto.response.OnlineResponse;
import com.tinjaku.dto.response.UserResponse;
import com.tinjaku.model.StatusOnOff;
import com.tinjaku.model.User;
import com.tinjaku.service.UserService;

@WebMvcTest(UserController.class)
public class UserControllerTest {
    
    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    UserService userService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void shouldTambahUser() throws Exception {

        UserRequest request = new UserRequest();
        request.setNamaDepan("Jeremy");
        request.setNamaBelakang("Darma");
        request.setEmail("Example@gmail.com");

        UserResponse response = new UserResponse();
        response.setNamaDepan("Jeremy");

        when(userService.tambahUser(any(UserRequest.class)))
                .thenReturn(response);

        mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.namaDepan").value("Jeremy"));
    }

    @Test
    public void shouldOnlineMitra() throws Exception{

        User user = new User();
        user.setUserId(1L);

        OnlineRequest request = new OnlineRequest();
        request.setStatusOnOff(StatusOnOff.ONLINE);

        OnlineResponse response = new OnlineResponse();
        response.setStatusOnOff(StatusOnOff.ONLINE);

        when(userService.getUserOnline(eq(1L), any(OnlineRequest.class)))
                .thenReturn(response);

        mockMvc.perform(post("/user/1/online")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusOnOff").value("ONLINE"));
    }

    @Test
    public void shouldGetAllUser() throws Exception{

        UserResponse response = new UserResponse();
        response.setNamaDepan("Jeremy");

        UserResponse response2 = new UserResponse();
        response2.setNamaDepan("Prety");

        when(userService.getAllUser())
                .thenReturn(List.of(response, response2));

        mockMvc.perform(get("/user"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].namaDepan").value("Jeremy"))
                .andExpect(jsonPath("$[1].namaDepan").value("Prety"));

        verify(userService).getAllUser();

    }

    @Test
    public void shouldUserById() throws Exception{
        
        User user = new User();
        user.setUserId(1L);

        UserResponse response = new UserResponse();
        response.setNamaDepan("Jeremy");

        when(userService.getUserResponseById(1L))
                .thenReturn(response);

        mockMvc.perform(get("/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.namaDepan").value("Jeremy"));
    }

    @Test
    public void shouldDeleteUserById() throws Exception{

        doNothing().when(userService).deleteUserById(1L);
        
        mockMvc.perform(delete("/user/1"))
                .andExpect(status().isOk());

        verify(userService).deleteUserById(1L);
    }
}