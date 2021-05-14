package com.example.braiveassignment.controllers;

import com.example.braiveassignment.Model.FlightsEntity;
import com.example.braiveassignment.services.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class FlightAPIController {
    @Autowired
    private FlightService service;



    


    @PostMapping(path="/api/update")
    public void editFlight(@RequestParam int id,
                             @RequestParam(required = false) String name,
                             @RequestParam(required = false) String number,
                             @RequestParam(required = false) LocalDateTime scheduledTime,
                             @RequestParam(required = false) LocalDateTime arrivalTime,
                             @RequestParam(required = false) String departure,
                             @RequestParam(required = false) String destination,
                             @RequestParam(required = false) Double fare
                             )
    {
        FlightsEntity flight= service.findById(id);
        if(name!=null)
            flight.setName(name);
        if(number!=null)
            flight.setNumber(number);
        if(scheduledTime!=null)
            flight.setScheduledTime(scheduledTime);
        if(arrivalTime!=null)
            flight.setArrivalTime(arrivalTime);
        if(departure!=null)
            flight.setDeparture(departure);
        if(destination!=null)
            flight.setDestination(destination);
        if(fare!=null)
            flight.setFare(fare);
        flight.setDuration(ChronoUnit.HOURS.between(flight.getScheduledTime(),flight.getArrivalTime()));
        service.saveFlight(flight);

    }

    @PostMapping(path="/api/add")
    public void createFlight(@RequestParam String name,
                           @RequestParam String number,
                           @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")LocalDateTime scheduledTime,
                           @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")LocalDateTime arrivalTime,
                           @RequestParam String departure,
                           @RequestParam String destination,
                           @RequestParam Double fare
    )
    {
        FlightsEntity flight= new FlightsEntity();

            flight.setName(name);

            flight.setNumber(number);

            flight.setScheduledTime(scheduledTime);

            flight.setArrivalTime(arrivalTime);

            flight.setDeparture(departure);

            flight.setDestination(destination);

            flight.setFare(fare);
        flight.setDuration(ChronoUnit.HOURS.between(flight.getScheduledTime(),flight.getArrivalTime()));
        service.saveFlight(flight);

    }



    @DeleteMapping(path="/api/delete")
    public void deleteFlight(@RequestParam int id)
    {
        service.deleteById(id);

    }

    @GetMapping(path="/api/search")
    public ResponseEntity <Map<String,Object>> findPaginated(@RequestParam(required = false) String name,
                                                             @RequestParam(required =false) String departure,
                                                             @RequestParam(required =false) String destination,
                                                             @RequestParam(required = false)LocalDateTime scheduled,
                                                             @RequestParam(defaultValue = "1") int pageNumber,
                                                             @RequestParam(defaultValue="2") int pageSize)
    {
        try {


            Page<FlightsEntity> flights=service.search(name,departure,destination,scheduled,pageNumber,pageSize);


            Map<String,Object> response= new HashMap<>();
            response.put("flights",flights.getContent());
            response.put("currentPage",flights.getNumber()+1);
            response.put("totalItems",flights.getTotalElements());
            response.put("totalPages",flights.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
        }
catch (Exception e)
{
    return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
    }
    }

    @GetMapping(path="/api/showAll")
    public ResponseEntity<Map<String,Object>> showAll(
                                                             @RequestParam(defaultValue = "1") int pageNumber,
                                                             @RequestParam(defaultValue="2") int pageSize)
    {
        try {


            Page<FlightsEntity> flights=service.findPaginated(pageNumber,pageSize);


            Map<String , Object> response= new HashMap<>();
            response.put("flights",flights.getContent());
            response.put("currentPage",flights.getNumber());
            response.put("totalItems",flights.getTotalElements());
            response.put("totalPages",flights.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}











