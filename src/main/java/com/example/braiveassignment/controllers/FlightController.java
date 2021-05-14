package com.example.braiveassignment.controllers;

import com.example.braiveassignment.Model.FlightsEntity;
import com.example.braiveassignment.Model.SearchCriteria;
import com.example.braiveassignment.services.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Controller
public class FlightController {
    @Autowired
    private FlightService service;
    /*@RequestMapping(
            method={RequestMethod.POST,RequestMethod.GET,RequestMethod.PUT}
    )*/



    @GetMapping(path="/")
    public String getAll(Model model)
    {
        return findPaginated(1,model);
    }

    @PostMapping(path="/edit")
    public String editFlight(@ModelAttribute FlightsEntity flight, Model model,
                             BindingResult result)
    {

        service.saveFlight(flight);
        return "success_edit";
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

        return "success_creation";
    }

    @GetMapping(path="/create")
    public String initFlight(Model model)
    {

        FlightsEntity flight=new FlightsEntity();

        model.addAttribute("flight",flight);

        return "create";
    }

    @GetMapping(path="/edit/{id}")
    public String getFlight( Model model, @PathVariable(value="id") int id)
    {
        FlightsEntity flight=service.findById(id);
        model.addAttribute("flight",flight);
        return "edit";
    }

    @GetMapping(path="/delete/{id}")
    public String deleteFlight(Model model, @PathVariable(value="id") int id)
    {
        service.deleteById(id);
        return "success_delete";
    }

    @GetMapping(path="/page/{pageNumber}")
    public String findPaginated(@PathVariable(value="pageNumber") int pageNumber, Model model)
    {
        int pageSize=2;
        Page<FlightsEntity> page=service.findPaginated(pageNumber,pageSize);
        List<FlightsEntity> flights=page.getContent();
        model.addAttribute("currentPage",pageNumber);
        model.addAttribute("totalPages",page.getTotalPages());
        model.addAttribute("totalFlights",page.getTotalElements());
        model.addAttribute("flights",flights);

        return "index";

    }

    @GetMapping(path="/search")
    public String initSearch(Model model)
    {

        SearchCriteria search=new SearchCriteria();

        model.addAttribute("search",search);

        return "search";
    }


    @PostMapping(path="/search")
    public String showResults(
                              @RequestParam(defaultValue = "1") int pageNumber,
                              @RequestParam(defaultValue="2") int pageSize,
                              @ModelAttribute SearchCriteria search, Model model,
                              BindingResult result)
    {


            Page<FlightsEntity> pageFlights=service.search(search.getName(),search.getDeparture(),search.getDestination(),search.getScheduledTime(),pageNumber,pageSize);
        List<FlightsEntity> flights=pageFlights.getContent();
        model.addAttribute("currentPage",pageNumber);
        model.addAttribute("totalPages",pageFlights.getTotalPages());
        model.addAttribute("totalFlights",pageFlights.getTotalElements());
        model.addAttribute("flights",flights);
            return "searchresult";

    }


    @GetMapping(path="/success")
    public String showSuccess()
    {
        return "success";
    }



}



