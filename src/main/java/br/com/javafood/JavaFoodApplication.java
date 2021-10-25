package br.com.javafood;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@SpringBootApplication()
public class JavaFoodApplication {

    public static void main(String[] args) {
        SpringApplication.run(JavaFoodApplication.class, args);
    }

}
