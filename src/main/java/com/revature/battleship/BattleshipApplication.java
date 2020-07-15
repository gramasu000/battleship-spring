package com.revature.battleship;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;


@SpringBootApplication
public class BattleshipApplication {

	public static void main(String[] args) {
		SpringApplication.run(BattleshipApplication.class, args);
	}
  
  @Bean
  ApplicationRunner applicationRunner(Environment environment) {
    return args -> {
      System.out.println(environment.getProperty("spring.data.mongodb.uri"));
    };
  }


}
