package com.pubmatic.curatedaudience.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@ComponentScan({"com.pubmatic.curatedaudience"})
@EnableAutoConfiguration
@SpringBootApplication
@EnableScheduling
public class CuratedAudienceSchedulerApplication {
	public static void main(String[] args) {
		SpringApplication.run(CuratedAudienceSchedulerApplication.class, args);
	}
}
