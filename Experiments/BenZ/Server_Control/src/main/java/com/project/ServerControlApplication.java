package com.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;



@SpringBootApplication
@EntityScan({"com.project.user", "com.project.userSteps", "com.project.userExercise", "com.project.Exercise"})
@ComponentScan({"com.project.user", "com.project.userSteps, com.project.userExercise, com.project.Exercise"})
public class ServerControlApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerControlApplication.class, args);
    }
}
