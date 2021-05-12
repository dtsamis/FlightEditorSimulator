package com.example.braiveassignment.repositories;

import com.example.braiveassignment.Model.FlightsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface FlightRepository extends JpaRepository<FlightsEntity,Integer> {
    Optional<FlightsEntity> findById(Integer id);
    List<FlightsEntity> findAll();
    Optional<FlightsEntity> findFirstByNameAndDepartureAndDestinationAndScheduledTime(String name, String departure, String destination, LocalDateTime scheduled);

}
