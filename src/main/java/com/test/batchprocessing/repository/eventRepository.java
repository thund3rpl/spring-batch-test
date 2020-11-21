package com.test.batchprocessing.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.test.batchprocessing.model.eventModel;


@Repository
public interface eventRepository extends JpaRepository<eventModel, String> {
	
}
