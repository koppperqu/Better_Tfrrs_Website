package com.bettertfrrs.db;

import com.bettertfrrs.db.entities.Athlete;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DB extends JpaRepository<Athlete,Integer> {

}
