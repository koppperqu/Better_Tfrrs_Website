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
}
