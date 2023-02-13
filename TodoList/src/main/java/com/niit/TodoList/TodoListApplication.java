package com.niit.TodoList;

import com.niit.TodoList.Filter.Filter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableFeignClients
@EnableEurekaClient
public class TodoListApplication {

	public static void main(String[] args) {
		SpringApplication.run(TodoListApplication.class, args);
	}

	@Bean
	public FilterRegistrationBean filter(){
		FilterRegistrationBean frb=new FilterRegistrationBean<>();
		frb.setFilter(new Filter());
		frb.addUrlPatterns("/v2/getone/*");
		return frb;
	}

}
