package com.portwood.javatruckgraphql;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

@SpringBootApplication
@EnableWebSocket
public class JavaTruckGraphqlApplication {

	public static void main(String[] args) {
		SpringApplication.run(JavaTruckGraphqlApplication.class, args);
	}

}
