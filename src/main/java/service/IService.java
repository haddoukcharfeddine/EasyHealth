package service;

import entite.Users.User;

import java.sql.SQLException;

public interface IService <T>{
    void addUser(T t);
    T getUserById(int id);
    void updateUser(T t);
    void deleteUser(int id);
    User getUserByTelephone(String telephone);
    void deleteUserByTelephone(String telephone);

    boolean checkTelephoneExists(String telephone) throws SQLException;

    boolean validateLogin(String telephone, String password) throws SQLException;


//Menu


}
