package org.robey.shutdwon_hook_limit;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController // Combines @SpringBootApplication and @RestController in one class
@RequestMapping("/api") // Base URL for the REST endpoints
public class ShutdownHookApp {

    public static void main(String[] args) {
        SpringApplication.run(ShutdownHookApp.class, args);
    }

    @GetMapping("/hello")
    public String sayHello() {
        return "Hello, World!";
    }
}