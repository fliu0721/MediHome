package com.medihome;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 家庭药品管理系统启动类
 */
@SpringBootApplication
@EnableScheduling
public class MediHomeApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(MediHomeApplication.class, args);
    }
}
