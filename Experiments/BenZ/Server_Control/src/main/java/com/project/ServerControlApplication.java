package com.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.project.user.UserController;

@SpringBootApplication
@ComponentScan(basePackageClasses = UserController.class)
public class ServerControlApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerControlApplication.class, args);
    }
}
