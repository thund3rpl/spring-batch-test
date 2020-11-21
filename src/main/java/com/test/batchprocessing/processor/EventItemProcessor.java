package com.test.batchprocessing.processor;

import com.test.batchprocessing.repository.eventRepository;


import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import com.test.batchprocessing.model.eventModel;

public class EventItemProcessor implements ItemProcessor<eventModel, eventModel> {

	private static final Logger log = LoggerFactory.getLogger(EventItemProcessor.class);

	 @Autowired
	 eventRepository eventRepository;
	
	 /**
	 * This is a a function which process the incoming event
	 *  
	 * @param  incoming eventModel
	 * @return new eventModel object
	 */
	public eventModel process(final eventModel event) {
		final String id = event.getId();
		final String state = event.getState();
		final String host =  event.getHost();
		final String type = event.getType();
		final long timestamp = event.getTimestamp();

		eventModel transformedEvent = null;
		
		log.info("PROCESSOR: started processing " + event);
		Optional<eventModel> e=eventRepository.findById(id);
		if (e.isPresent()) {
			eventModel em=e.get();
			log.info("PROCESSOR: event:" + em.getId() + ": event already in db: " + em);
			if (em.getState().equals("FINISHED")) {
				log.info("PROCESSOR: event:" + em.getId() + ": ignoring already FINISHED event " + e);
			} else {
				transformedEvent = new eventModel(id, state, host, type, timestamp);
				
				long duration = transformedEvent.getTimestamp() - em.getTimestamp();
				
				if (duration > 4) {
					transformedEvent.setAlert(true);
				}
				log.info("PROCESSOR: event:" + em.getId() + ": updating event to FINISHED state duration: " + duration + " " + transformedEvent);
			}
			
		} else {
			transformedEvent = new eventModel(id, state, host, type, timestamp);
			log.info("PROCESSOR: event:" + transformedEvent.getId() + ": new event " + transformedEvent);
		}
		


		return transformedEvent;
	}

}
