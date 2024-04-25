package kr.ac.kopo.guestbook0418;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class GuestBook0418Application {

    public static void main(String[] args) {
        SpringApplication.run(GuestBook0418Application.class, args);
    }

}
