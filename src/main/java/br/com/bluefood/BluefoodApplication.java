package br.com.bluefood;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

//@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@SpringBootApplication()
public class BluefoodApplication {

    public static void main(String[] args) {
        SpringApplication.run(BluefoodApplication.class, args);
    }

}
