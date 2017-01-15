package com.company;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import com.company.app.criteria.query.QueryCriteria;
import com.company.app.criteria.query.QueryCriteriaGenerator;
import com.company.app.criteria.response.MediaResponse;
import com.company.app.service.MediaSearchService;

/**
 * Main entry point of the application.
 * 
 * @author Gustavo
 *
 */
@SpringBootApplication
@ComponentScan("com.company.config")
public class MediaSearchServiceApplication {

	public static void main(String[] args) {
		
		final ConfigurableApplicationContext applicationContext = SpringApplication.run(MediaSearchServiceApplication.class, args);
		
		final QueryCriteriaGenerator queryCriteriaGenerator = (QueryCriteriaGenerator) applicationContext.getBean("environmentQueryCriteriaGenerator");
		final QueryCriteria queryCriteria = queryCriteriaGenerator.generate();
		
		final MediaSearchService mediaSearchService = (MediaSearchService) applicationContext.getBean("mediaSearchService");
		final MediaResponse mediaResponse = mediaSearchService.search(queryCriteria);
		
		mediaResponse.printResponse();
	}
}
