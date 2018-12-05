package pajakin.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.MongoClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;

@Configuration
class MongoConfig {

    public @Bean
	MongoTemplate mongoTemplate() throws Exception {
		
		MongoTemplate mongoTemplate = 
			new MongoTemplate(new MongoClient("127.0.0.1"), "PBO");
		return mongoTemplate;
		
	}

	
}

public class Database {
	
	public static ApplicationContext ctx = 
        new AnnotationConfigApplicationContext(MongoConfig.class);
    public static MongoOperations mongoOperation = 
        (MongoOperations) ctx.getBean("mongoTemplate");

}
