package org.example;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Administrator
 */
@SpringBootApplication
@EnableAdminServer
public class SpringBootAdmin {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootAdmin.class, args);
    }
}