package com.micro;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.micro.cassandra.Cassandra;

@SpringBootApplication
public class PolicyManagerApp {
	public static void main(String[] args) {
		SpringApplication.run(PolicyManagerApp.class, args);
	}
}
