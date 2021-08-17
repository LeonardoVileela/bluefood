package br.com.javafood;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@SpringBootApplication()
public class BluefoodApplication {

    public static void main(String[] args) {
        SpringApplication.run(BluefoodApplication.class, args);
    }

}
