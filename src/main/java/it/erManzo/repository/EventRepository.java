package it.erManzo.repository;

import org.springframework.data.repository.CrudRepository;

import it.erManzo.model.Event;

public interface EventRepository extends CrudRepository<Event, Integer>{
	
}
