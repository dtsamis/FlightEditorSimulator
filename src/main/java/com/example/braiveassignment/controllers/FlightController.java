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

/***
 * This is the controller for all internal calls executed when user uses the graphical interface
 */
@Controller
public class FlightController {
    @Autowired
    private FlightService service;
    /*@RequestMapping(
            method={RequestMethod.POST,RequestMethod.GET,RequestMethod.PUT}
    )*/


    /***
     * Since thymeleaf is used as a template engine, a model variable is required for passing values between calls
     * This url is the home url and displays the list of all flihgts
     * as well as the buttons that can be used for CRUD operations
     * @param model
     * @return
     */
    @GetMapping(path="/")
    public String getAll(Model model)
    {
        return findPaginated(1,model);
    }

    /***
     * This url is used for updating a flight.
     * @param flight
     * @param model
     * @param result
     * @return
     */
    @PostMapping(path="/edit")
    public String editFlight(@ModelAttribute FlightsEntity flight, Model model,
                             BindingResult result)
    {

        service.saveFlight(flight);
        return "success_edit";
    }

    /***
     * This is the url used for getting the data of the update form
     * @param flight
     * @param model
     * @param result
     * @return
     */
    @GetMapping(path="/edit")
    public String getFlightInfo(@ModelAttribute FlightsEntity flight, Model model, BindingResult result)
    {

        model.addAttribute("flight",flight);
        return "edit";
    }

    /***
     * This is the url used for creating a flight
     * @param flight
     * @param model
     * @param result
     * @return
     */
    @PostMapping(path="/create")
    public String createFlight(@ModelAttribute FlightsEntity flight, Model model,
                               BindingResult result)
    {

        //Duration is calculated automatically
        flight.setDuration(ChronoUnit.HOURS.between(flight.getScheduledTime(),flight.getArrivalTime()));
        service.saveFlight(flight);

        return "success_creation";
    }

    /***
     * This url provides an empty form for adding the fields when creating a  new flight
     * @param model
     * @return
     */
    @GetMapping(path="/create")
    public String initFlight(Model model)
    {

        FlightsEntity flight=new FlightsEntity();

        model.addAttribute("flight",flight);

        return "create";
    }

    /***
     * This is the url that takes the field values of a flight with a specific id
     * and displays them as default values im the update windows
     * @param model
     * @param id
     * @return
     */
    @GetMapping(path="/edit/{id}")
    public String getFlight( Model model, @PathVariable(value="id") int id)
    {
        FlightsEntity flight=service.findById(id);
        model.addAttribute("flight",flight);
        return "edit";
    }

    /***
     * This is the url when user wants to delete a flight
     * Deletion is done by using id as parameter.
     * @param model
     * @param id
     * @return
     */
    @GetMapping(path="/delete/{id}")
    public String deleteFlight(Model model, @PathVariable(value="id") int id)
    {
        service.deleteById(id);
        return "success_delete";
    }

    /***
     * This is a helper method that offers pagination for the results
     * @param pageNumber
     * @param model
     * @return
     */
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

    /***
     * this is the url that gives an empty search form to the user
     * @param model
     * @return
     */
    @GetMapping(path="/search")
    public String initSearch(Model model)
    {

        SearchCriteria search=new SearchCriteria();

        model.addAttribute("search",search);

        return "search";
    }

    /***
     * this is the url that does the actual search.
     * Search results are shown as a union of the individual searches.
     * @param pageNumber
     * @param pageSize
     * @param search
     * @param model
     * @param result
     * @return
     */
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

    /***
     * This is the url for succesful results
     * @return
     */
    @GetMapping(path="/success")
    public String showSuccess()
    {
        return "success";
    }



}



