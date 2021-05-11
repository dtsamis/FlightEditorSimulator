package com.example.braiveassignment.controllers;

import com.example.braiveassignment.Model.FlightsEntity;
import com.example.braiveassignment.services.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Controller
public class FlightController {
    @Autowired
    private FlightService service;
    @RequestMapping(
            method={RequestMethod.POST,RequestMethod.GET,RequestMethod.PUT}
    )

    @GetMapping(path="/")
    public String getAll(Model model)
    {
        List<FlightsEntity> flights=service.findAll();
        model.addAttribute("flights",flights);
        return "index";
    }

    @PostMapping(path="/edit")
    public String editFlight(@ModelAttribute FlightsEntity flight, Model model,
                             BindingResult result)
    {


        FlightsEntity storedFlight= service.findById(flight.getId());
        storedFlight.setDestination(flight.getDestination());
        storedFlight.setNumber(flight.getNumber());
        storedFlight.setName(flight.getName());
        storedFlight.setDuration(flight.getDuration());
        storedFlight.setFare(flight.getFare());
        storedFlight.setDeparture(flight.getDeparture());
        storedFlight.setScheduledTime(flight.getScheduledTime());
        storedFlight.setArrivalTime(flight.getArrivalTime());
        service.saveFlight(storedFlight);

        return "success";
    }

    @GetMapping(path="/edit")
    public String getFlightInfo(@ModelAttribute FlightsEntity flight, Model model, BindingResult result)
    {

        model.addAttribute("flight",flight);
        return "edit";
    }
    @PostMapping(path="/create")
    public String createFlight(@ModelAttribute FlightsEntity flight, Model model,
                               BindingResult result)
    {


        flight.setDuration(ChronoUnit.HOURS.between(flight.getScheduledTime(),flight.getArrivalTime()));
        service.saveFlight(flight);

        return "success";
    }

    @GetMapping(path="/create")
    public String initFlight(Model model)
    {

        FlightsEntity flight=new FlightsEntity();

        model.addAttribute("flight",flight);

        return "create";
    }

    @GetMapping(path="/edit/{id}")
    public String getFlight( Model model, @PathVariable("id") int id)
    {
        FlightsEntity flight=service.findById(id);
        model.addAttribute("flight",flight);
        return "edit";
    }

    @GetMapping(path="/success")
    public String showSuccess()
    {
        return "success";
    }
}


