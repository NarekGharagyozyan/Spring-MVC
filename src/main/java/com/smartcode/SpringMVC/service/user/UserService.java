package com.smartcode.SpringMVC.service.user;


import com.smartcode.SpringMVC.model.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserService {

    void register(User user) ;

    User getUser(Long id);

    void login(String email, String password) throws Exception;

    void changePassword(String email,String newPassword,String repeatPassword);

    void deleteAccount(String email);

    void verify(String email, String code);

    List<User> findAllUsers();
}
