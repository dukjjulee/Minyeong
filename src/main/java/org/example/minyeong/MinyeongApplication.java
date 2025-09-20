package org.example.minyeong;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class MinyeongApplication {
    public static void main(String[] args) {
        SpringApplication.run(MinyeongApplication.class, args);
    }
}
