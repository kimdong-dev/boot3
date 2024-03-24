package com.henry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Boot3Application {

    public static void main(String[] args) { // henry 주석
        SpringApplication.run(Boot3Application.class, args);
        System.out.println("henry가 수정");
    }

}
