package org.robey.shutdown_hook_limit;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.robey.utils.Log.log;
import static org.robey.utils.Log.logError;

@SpringBootApplication
@RestController // Combines @SpringBootApplication and @RestController in one class
@RequestMapping("/api")
public class App {

    public static void main(String[] args) {
        log(">>>>> ShutdownHookApp Starting Up >>>>>");
        try {
            SpringApplication.run(App.class, args);

            final int NUMBER_OF_HOOKS = 1_000_000;
            MyShutdownHook myShutdownHook = new MyShutdownHook();
            myShutdownHook.register(NUMBER_OF_HOOKS);
            log(" ** All Shutdown Hooks Have Been Registered, number: "  + NUMBER_OF_HOOKS);
        } catch (Throwable t) {
            logError("***** ShutdownHookApp Fatal Error", t);
        } finally {
            log(">>>>> ShutdownHookApp Exiting >>>>>");
        }
    }

    @GetMapping("/hello")
    public String sayHello() {
        return "Hello, World!";
    }
}