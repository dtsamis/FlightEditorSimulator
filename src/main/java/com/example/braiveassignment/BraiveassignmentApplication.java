package com.example.braiveassignment;

import com.example.braiveassignment.Model.FlightsEntity;
import com.example.braiveassignment.services.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@SpringBootApplication
public class BraiveassignmentApplication implements CommandLineRunner {
@Autowired
    FlightService service;
   public static void main(String[] args) {
       SpringApplication.run(BraiveassignmentApplication.class, args);
   }



    @Override
    public void run(String... args) throws Exception {


        FlightsEntity flightsEntity=new FlightsEntity();
        flightsEntity.setDeparture("Athens");
        flightsEntity.setDestination("Stockholm");
        flightsEntity.setArrivalTime(LocalDateTime.now());
        flightsEntity.setScheduledTime(LocalDateTime.now().minusHours(2));
        flightsEntity.setDuration(ChronoUnit.HOURS.between(flightsEntity.getScheduledTime(),flightsEntity.getArrivalTime()));
        flightsEntity.setFare(34.5);
        flightsEntity.setName("A342");
        flightsEntity.setNumber("345");

        service.saveFlight(flightsEntity);
    }
}
