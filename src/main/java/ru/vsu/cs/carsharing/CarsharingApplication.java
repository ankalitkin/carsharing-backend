package ru.vsu.cs.carsharing;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import ru.vsu.cs.carsharing.service.InitializingService;

@SpringBootApplication(exclude = UserDetailsServiceAutoConfiguration.class)
@RequiredArgsConstructor
public class CarsharingApplication {
    private final InitializingService initializingService;

    public static void main(String[] args) {
        SpringApplication.run(CarsharingApplication.class, args);
    }

    @Bean
    public CommandLineRunner run() {
        return args -> initializingService.init();
    }

}
