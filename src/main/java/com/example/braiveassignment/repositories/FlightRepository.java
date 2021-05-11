package com.example.braiveassignment.repositories;

import com.example.braiveassignment.Model.FlightsEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FlightRepository extends CrudRepository<FlightsEntity,Integer> {
    Optional<FlightsEntity> findById(Integer id);
    List<FlightsEntity> findAll();

}
