package com.bettertfrrs.db.services;

import com.bettertfrrs.db.entities.Conference;
import com.bettertfrrs.db.repositories.ConferenceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConferenceService {
    private final ConferenceRepository conferenceRepository;

    public ConferenceService(ConferenceRepository conferenceRepository){
        this.conferenceRepository = conferenceRepository;
    }

    // Get all conferences
    public List<Conference> getAllConferences() {
        return conferenceRepository.findAll();
    }

    // Get an conference by ID
    public Optional<Conference> getConferenceById(int id) {
        return conferenceRepository.findById(id);
    }

    // Create a new conference
    public Conference createConference(String name) {
        Conference conference = new Conference();
        conference.setName(name);
        return conferenceRepository.save(conference);
    }

    public Conference createOrUpdate(Conference conference) {
        Conference conferenceInDB = getConferenceByLink(conference.link);
        if (conferenceInDB != null) {
            conferenceInDB.setName(conference.name);
            return conferenceRepository.save(conferenceInDB);
        }else {
            return conferenceRepository.save(conference);
        }
    }

    private Conference getConferenceByLink(String link) {
        return conferenceRepository.findByLink(link);
    }
}
