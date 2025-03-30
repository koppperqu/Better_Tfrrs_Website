package com.bettertfrrs.db.services;

import com.bettertfrrs.db.models.Best;
import com.bettertfrrs.db.repositories.BestRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BestService {
    private final BestRepository BestRepository;

    public BestService(BestRepository BestRepository){
        this.BestRepository = BestRepository;
    }

    // Get all Bests
    public List<Best> getAllBests() {
        return BestRepository.findAll();
    }

    // Get an Best by ID
    public Optional<Best> getBestById(int id) {
        return BestRepository.findById(id);
    }

    // Create a new Best
    public Best createBest(String name) {
        Best Best = new Best();
        return BestRepository.save(Best);
    }
}
