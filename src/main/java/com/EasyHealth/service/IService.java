package com.EasyHealth.service;

import com.EasyHealth.entite.Users.User;

public interface IService <T>{
    void addUser(T t);
    T getUserById(int id);
    void updateUser(T t);
    void deleteUser(int id);
    User getUserByTelephone(String telephone);
    void deleteUserByTelephone(String telephone);

//Menu


}
