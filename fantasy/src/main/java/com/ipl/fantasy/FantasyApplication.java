package com.ipl.fantasy;

import com.ipl.fantasy.data.BallByBallDataHandler;
import com.ipl.fantasy.data.BallByBallDataHandlerImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableAutoConfiguration
public class FantasyApplication {

	public static void main(String[] args) {
		SpringApplication.run(FantasyApplication.class, args);
	}

}
