package it.erManzo.service;

import org.springframework.stereotype.Service;

import it.erManzo.model.Event;

@Service
public interface EventService {
	
	boolean controllo (Event event);

}
