package it.erManzo.controller;

import java.util.ArrayList;

import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.erManzo.model.Event;
import it.erManzo.repository.EventRepository;
import it.erManzo.service.EventService;

@RestController
@RequestMapping("/event")
public class EventController {

	private final Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

	@Autowired
	private EventRepository eventRepo;
	@Autowired
	private EventService eventServ;

	@GetMapping("/getModel")
	public Event getModel() {
		System.out.println(LocalDateTime.now());
		return new Event();
	}

	@PostMapping("/createEvent")
	public ResponseEntity<Event> createEvent(@RequestBody Event event) {
		try {
			Event saved = new Event();
			if (eventServ.controllo(event)) {
				saved = eventRepo.save(event);
			} else {
				return new ResponseEntity<>(HttpStatus.CONFLICT);
			}
			return new ResponseEntity<>(saved, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Event> deleteEvent(@PathVariable("id") Integer eventId) {
		try {
			eventRepo.delete(eventId);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/findOne/{idEvent}")
	public ResponseEntity<Event> findOne(@PathVariable("idEvent") Integer idEvent) {
		try {
			Event found = eventRepo.findOne(idEvent);
			return new ResponseEntity<>(found, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/getAllEvent")
	public ResponseEntity<Iterable<Event>> readAllEvent() {
		try {
			ArrayList<Event> lista = (ArrayList<Event>) eventRepo.findAll();
			return new ResponseEntity<>(lista, HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
