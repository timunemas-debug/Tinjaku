package com.tinjaku.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.random.*;

import org.springframework.stereotype.Service;

import com.tinjaku.model.User;
import com.tinjaku.dto.PesananRequest;
import com.tinjaku.dto.UserRequest;
import com.tinjaku.exception.ResourceNotFound;
import com.tinjaku.model.Pesanan;
import com.tinjaku.model.StatusPesanan;

@Service
public class UserService {
    private List<User> userList = new ArrayList<>();

    public User tambahUser(UserRequest request){
        User user = new User();

        user.setUserId((long) (userList.size() + 1));
        user.setNamaUser(request.getNamaUser());
        user.setAlamatLengkap(request.getAlamatUser());
        user.setKota(request.getKota());
        user.setPesananList(new ArrayList<>());

        userList.add(user);

        return user;
    }

    public List<User> getAllUser(){
        return userList;
    }

    public User getUserById(Long userId){
        return userList.stream()
               .filter(u -> u.getUserId().equals(userId))
               .findFirst()
               .orElseThrow(() ->
                new ResourceNotFound("User tidak ditemukan"));
            }

    public User deleteUserById(Long userId){
        User user = userList.stream()
                    .filter(u -> u.getUserId().equals(userId))
                    .findFirst()
                    .orElseThrow(() ->
                    new ResourceNotFound("User tidak ditemukan!"));
        userList.remove(user);

        return user;
    }

    public User tambahPesananUser(Long userId, PesananRequest request){
        for(User user : userList){
            if(user.getUserId().equals(userId)){
                Random random = new Random();

                Pesanan pesanan = new Pesanan();
                pesanan.setId((long) random.nextInt(10000));
                pesanan.setUser(user);
                pesanan.setKeluhan(request.getKeluhan());
                pesanan.setStatus(StatusPesanan.MENUNGGU);

                user.getPesananList().add(pesanan);

                return user;

            }
        }
        throw new ResourceNotFound("User dengan Id : " + userId + " tidak ditemukan");
    }
}