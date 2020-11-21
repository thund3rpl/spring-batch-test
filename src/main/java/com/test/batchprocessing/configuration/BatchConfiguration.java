package com.test.batchprocessing.configuration;

import java.net.MalformedURLException;

import javax.persistence.EntityManagerFactory;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.test.batchprocessing.model.eventModel;
import com.test.batchprocessing.notification.JobCompletionNotificationListener;
import com.test.batchprocessing.processor.EventItemProcessor;
import com.test.batchprocessing.reader.jsonReader;


@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

	
	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	@Autowired
    EntityManagerFactory emf;



	

	@Bean
	public EventItemProcessor processor() {
		return new EventItemProcessor();
	}



	
	 /**
		 * This is a a function writes processed object to Jpa repository
		 *  
		 * @return JpaItemWriter
	*/
	@Bean
	public JpaItemWriter writer() {
		JpaItemWriter writer = new JpaItemWriter();
		writer.setEntityManagerFactory(emf);
        return writer;
	}

	 /**
		 * This is a a import job definition function
		 *  
		 * @param listener function
		 * @param step function
		 * @return new Job
	*/
	@Bean
	public Job importEvents(JobCompletionNotificationListener listener, Step step1) {
		return jobBuilderFactory.get("importEventsJob")
			.incrementer(new RunIdIncrementer())
			.listener(listener)
			.flow(step1)
			.end()
			.build();
	}

	/**
	 * This is a job step definition
	 *  
	 * @param JpaItemWriter function to which transformed objects are written
	 * @return new Step
	 */
	@Bean
	public Step step1(JpaItemWriter writer) throws MalformedURLException {
		jsonReader reader = new jsonReader();
		return stepBuilderFactory.get("step1")
			.<eventModel, eventModel> chunk(1)
			.reader(reader.jsonItemReader())
			.processor(processor())
			.writer(writer())
			.build();
	}

	
}
