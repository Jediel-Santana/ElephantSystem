package edu.uniaeso.ElephantSystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class ElephantSystemApplication {
	
	private static ApplicationContext APPLICATION_CONTEXT;
	
	public static void main(String[] args) {
		APPLICATION_CONTEXT = SpringApplication.run(ElephantSystemApplication.class, args);
	}
	
	public static <T> T getBean(Class<T> classe){
		return APPLICATION_CONTEXT.getBean(classe);
	}
	

}
