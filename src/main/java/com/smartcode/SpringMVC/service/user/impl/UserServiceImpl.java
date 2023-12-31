package com.smartcode.SpringMVC.service.user.impl;

import com.smartcode.SpringMVC.model.User;
import com.smartcode.SpringMVC.exceptions.*;
import com.smartcode.SpringMVC.repository.UserRepository;
import com.smartcode.SpringMVC.service.email.EmailService;
import com.smartcode.SpringMVC.service.user.UserService;
import com.smartcode.SpringMVC.util.codeGenerator.RandomGenerator;
import com.smartcode.SpringMVC.util.constants.Message;
import com.smartcode.SpringMVC.util.encoder.MD5Encoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @Override
    @Transactional
    public void register(User user) {
            validationForRegistration(user);
            String generatedCode = RandomGenerator.generateNumericString(6);
            user.setPassword(MD5Encoder.encode(user.getPassword()));
            user.setCode(generatedCode);
            userRepository.save(user);
            emailService.sendSimpleMessage(user.getEmail(),Message.VERIFICATION_CODE_SUBJECT,"Your verification code is: " + generatedCode);
    }


    @Override
    @Transactional(readOnly = true)
    public User getUser(Long id) {
        Optional<User> byId = userRepository.findById(id);
        return byId.orElseThrow(() -> new UserNotFoundException(Message.USER_NOT_FOUNT));
    }

    @Override
    @Transactional
    public void login(String email, String password) {
        validationForLogin(email, password);
        User loginedUser = userRepository.findByEmail(email);

        if (loginedUser == null)
            throw new UserNotFoundException(Message.USER_NOT_FOUNT);
        if (!loginedUser.isVerified()){
            throw new VerificationException(Message.VERIFY_ACCOUNT);
        }
        if (!loginedUser.getPassword().equals(MD5Encoder.encode(password))) {
            throw new ValidationException(Message.WRONG_EMAIL_OR_PASSWORD);
        }
    }

    @Override
    @Transactional
    public void changePassword(String email, String newPassword, String repeatPassword) {
        if (!newPassword.equals(repeatPassword)) {
            throw new ValidationException(Message.PASSWORDS_NOT_MATCHES);
        }
        var user = userRepository.findByEmail(email);
        if (user == null)
            throw new UserNotFoundException(Message.USER_NOT_FOUNT);
        passwordValidation(newPassword);
        user.setPassword(MD5Encoder.encode(newPassword));
        try {
            userRepository.save(user);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional
    public void deleteAccount(String email) {
        try {
            User byEmail = userRepository.findByEmail(email);
            userRepository.delete(byEmail);
        } catch (Exception e) {
            throw new UserNotFoundException(Message.USER_NOT_FOUNT);
        }
    }

    @Override
    @Transactional
    public void verify(String email, String code) {
            User user = userRepository.findByEmail(email);
            if (user == null) {
                throw new UserNotFoundException(Message.USER_NOT_FOUNT);
            }

            if (!user.getCode().equals(code)){
                throw new ValidationException(Message.INCORRECT_CODE);
            }
            user.setVerified(true);
            userRepository.save(user);
    }

    @Override
    @Transactional
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    private void validationForRegistration(User user) {
        if (user.getEmail() == null ||
                user.getEmail().length() == 0 ||
                !user.getEmail().matches("^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*"
                        + "@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$"))
            throw new ValidationException(Message.EMAIL_OR_PASSWORD_IS_NULL);
        if (user.getPassword() == null ||
                user.getPassword().length() < 8
        )
            throw new ValidationException(Message.PASSWORD_VALIDATION_IS_WRONG);
    }

    private void passwordValidation(String password) {
        if (password.length() < 8)
            throw new ValidationException(Message.PASSWORD_LENGTH_ISSUE);
    }

    private void validationForLogin(String email, String password) {
        if (email == null || password == null || email.isEmpty() || password.isEmpty())
            throw new ValidationException(Message.EMAIL_OR_PASSWORD_IS_NULL);
    }


}
