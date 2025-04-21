package com.bettertfrrs.db.services;

import com.bettertfrrs.db.entities.Event;
import com.bettertfrrs.db.repositories.EventRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventService {
    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository){
        this.eventRepository = eventRepository;
    }

    // Get all events
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    // Get an event by ID
    public Optional<Event> getEventById(int id) {
        return eventRepository.findById(id);
    }

    // Create a new event
    public Event createEvent(String name) {
        Event event = new Event();
        event.setName(name);
        return eventRepository.save(event);
    }

    public Event createOrUpdate(Event event) {
        Event eventInDB = getEventByShortName(event.shortName);
        if (eventInDB != null) {
            eventInDB.setShortName(event.shortName);
            eventInDB.setName(getEventName(eventInDB.shortName));
            return eventRepository.save(eventInDB);
        }else {
            event.setName(getEventName(event.shortName));
            return eventRepository.save(event);
        }
    }

    private Event getEventByShortName(String shortName) {
        return eventRepository.findByShortName(shortName);
    }

    private String getEventName(String shortName) {
        return switch (shortName) {
            case "110H" -> "110 Hurdles";
            case "100H" -> "100 Hurdles";
            case "400H" -> "400 Hurdles";
            case "3000S" -> "3000 Steeplechase";
            case "HJ" -> "High Jump";
            case "PV" -> "Pole Vault";
            case "LJ" -> "Long Jump";
            case "SP" -> "Shot Put";
            case "DT" -> "Discus";
            case "HT" -> "Hammer";
            case "JT" -> "Javelin";
            case "Dec" -> "Decathlon";
            case "55H" -> "55 Hurdles";
            case "60H" -> "60 Hurdles";
            case "WT" -> "Weight";
            case "Hep" -> "Heptathlon";
            case "Pent" -> "Pentathlon";
            default -> shortName;
        };
    }
}
