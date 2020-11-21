package com.test.batchprocessing.reader;

import java.io.File;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.json.JacksonJsonObjectReader;
import org.springframework.batch.item.json.JsonItemReader;
import org.springframework.batch.item.json.builder.JsonItemReaderBuilder;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import com.test.batchprocessing.model.eventModel;


@Scope("step")
public class jsonReader {
	private static final Logger log = LoggerFactory.getLogger(jsonReader.class);
	
	 /**
		 * This is a a function which reads json file and maps each event to eventModel
		 *  
		 * @return JsonItemReader
		 */
	@Bean
    public JsonItemReader jsonItemReader() {
		JsonItemReader ir = null;
		String filePath=System.getProperty("filePath");
		File f = new File(filePath);
		Resource resource = new FileSystemResource(f);
		if (resource.exists()) {
			log.info("READER: reading from file: " + f.getAbsolutePath());
			ir = new JsonItemReaderBuilder()
					.jsonObjectReader(new JacksonJsonObjectReader(eventModel.class))
					.resource(resource)
					.name("eventJsonItemReader")
					.build();
		} else {
			log.error("READER: " + filePath + " not found");
		}
		
		return ir;
    }
	
	
}
