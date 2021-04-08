package com.gmart.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@EnableAutoConfiguration
@EnableZuulProxy
@EnableResourceServer
@SpringBootApplication
@EnableDiscoveryClient
public class GmartApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(GmartApiGatewayApplication.class, args);
	}

}
