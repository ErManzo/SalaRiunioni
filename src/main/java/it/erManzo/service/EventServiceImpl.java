package it.erManzo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.erManzo.model.Event;
import it.erManzo.repository.EventRepository;

@Service
public class EventServiceImpl implements EventService {

	@Autowired
	EventRepository eventRepo;

	@Override
	public boolean controllo(Event event) {
		List<Event> listaEventi = (List<Event>) eventRepo.findAll();
		String parseStart = "";
		String parseEnd = "";
		for (Event e : listaEventi) {
			String giornoSalvato = e.getStartTime().toString().substring(0, 10);
			// System.out.println("salvato " + giornoSalvato);
			String giornoDaSalvare = event.getStartTime().toString().substring(0, 10);
			// System.out.println("da salvare " + giornoDaSalvare);

			if (giornoSalvato.equals(giornoDaSalvare) && e.getId() != event.getId()) {

				int startSalvato = Integer.parseInt(e.getStartTime().toString().substring(11, 16).replaceAll(":", ""));
				int startDaSalvare = Integer
						.parseInt(event.getStartTime().toString().substring(11, 16).replaceAll(":", ""));
				int endSalvato = Integer.parseInt(e.getEndTime().toString().substring(11, 16).replaceAll(":", ""));
				int endDaSalvare = Integer
						.parseInt(event.getEndTime().toString().substring(11, 16).replaceAll(":", ""));

				if (startDaSalvare >= startSalvato && startDaSalvare < endSalvato
						|| endDaSalvare > startSalvato && endDaSalvare <= endSalvato
						|| startDaSalvare <= startSalvato && endDaSalvare >= endSalvato) {
					return false; // gia' prenotato
				} else if (e.isAllDay()) {
					return false; // gia' prenotato
				}

			}

		}
		return true; // disponibile
	}

}
