package com.bettertfrrs.db;

import com.bettertfrrs.db.models.Athlete;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DB extends JpaRepository<Athlete,Integer> {

}
