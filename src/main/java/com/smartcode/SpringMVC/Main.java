package com.smartcode.SpringMVC;


import com.smartcode.SpringMVC.repository.UserRepository;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {

        ApplicationContext context = new ClassPathXmlApplicationContext("application-context.xml");

        UserRepository bean = context.getBean(UserRepository.class);
        bean.findByEmail("asdasd");


    }

}
