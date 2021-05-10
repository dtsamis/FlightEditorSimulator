package com.example.braiveassignment.repositories;

import com.example.braiveassignment.Model.FlightsEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlightRepository extends CrudRepository<FlightsEntity,Integer> {
}
