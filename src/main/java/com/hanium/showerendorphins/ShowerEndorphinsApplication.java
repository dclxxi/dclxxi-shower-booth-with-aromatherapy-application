package com.hanium.showerendorphins;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class ShowerEndorphinsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShowerEndorphinsApplication.class, args);
    }

}
