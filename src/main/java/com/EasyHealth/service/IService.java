package com.EasyHealth.service;

import com.EasyHealth.entite.User;

import java.util.List;

public interface IService <T>{
    void addUser(T t);

    T getUserById(int id);


    void updateUser(T t);

    void deleteUser(int id);
}
