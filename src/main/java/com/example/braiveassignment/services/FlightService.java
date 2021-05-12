package com.example.braiveassignment.services;

import com.example.braiveassignment.Model.FlightsEntity;
import com.example.braiveassignment.repositories.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

@Service
public class FlightService {


    private FlightRepository flightRepository;

    public FlightRepository getRepository() {
        return flightRepository;
    }

    @Autowired
    public void setRepository(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    public void saveFlight(FlightsEntity flightsEntity) {
        flightRepository.save(flightsEntity);
    }

    public FlightsEntity findById(Integer id) {
        return flightRepository.findById(id).get();
    }

    public List<FlightsEntity> findAll() {
        return flightRepository.findAll();
    }

    public FlightsEntity search(String name, String departure, String destination, LocalDateTime scheduled, int pageNumber, int rowPerPage) {
        return flightRepository.findFirstByNameAndDepartureAndDestinationAndScheduledTime(name, departure, destination, scheduled).get();
    }

    public void deleteById(Integer id) {
        flightRepository.deleteById(id);
    }

    public Page<FlightsEntity> findPaginated(int pageNumber, int pageSize)

    {
        Pageable pageable = PageRequest.of(pageNumber-1,pageSize);
        return flightRepository.findAll(pageable);

    }


   public static void main(String[] args)
   {
       FlightService service=new FlightService();
       FlightsEntity flightsEntity=new FlightsEntity();
       flightsEntity.setDeparture("Athens");
       flightsEntity.setArrivalTime(LocalDateTime.now());
       flightsEntity.setScheduledTime(LocalDateTime.now().minusHours(2));
       flightsEntity.setDuration(ChronoUnit.HOURS.between(flightsEntity.getScheduledTime(),flightsEntity.getArrivalTime()));
       flightsEntity.setFare(34.5);
       flightsEntity.setName("A342");
       flightsEntity.setNumber("345");

       service.saveFlight(flightsEntity);
   }

}


