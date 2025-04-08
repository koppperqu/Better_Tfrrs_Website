package com.bettertfrrs.db.services;

import com.bettertfrrs.db.entities.Division;
import com.bettertfrrs.db.repositories.DivisionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DivisionService {
    private final DivisionRepository divisionRepository;

    public DivisionService(DivisionRepository divisionRepository){
        this.divisionRepository = divisionRepository;
    }

    // Get all divisions
    public List<Division> getAllDivisions() {
        return divisionRepository.findAll();
    }

    // Get an division by ID
    public Optional<Division> getDivisionById(int id) {
        return divisionRepository.findById(id);
    }

    // Create a new division
    public Division createDivision(String name) {
        Division division = new Division();
        division.setName(name);
        return divisionRepository.save(division);
    }
}
