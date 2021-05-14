package com.example.braiveassignment.repositories;

import com.example.braiveassignment.Model.FlightsEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/***
 * The repository that provides all the JPA entities operation for flights
 * Pageable is used for most of the methods as results pagination is desired.
 */
@Repository
public interface FlightRepository extends JpaRepository<FlightsEntity,Integer> {
    Optional<FlightsEntity> findById(Integer id);
    Page<FlightsEntity> findAll(Pageable pageable);
    Page<FlightsEntity> findByNameOrDepartureOrDestinationOrScheduledTime(String name, String departure, String destination, LocalDateTime scheduled, Pageable pageable);
    Page<FlightsEntity> findByName(String name,Pageable pageable);
    Page<FlightsEntity> findByScheduledTime(LocalDateTime dateTime,Pageable pageable);
    Page<FlightsEntity> findByDeparture(String departure, Pageable pageable);
    Page<FlightsEntity> findByDestination(String destination, Pageable pageable);
}
