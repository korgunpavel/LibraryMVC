package com.korgun.springcourse.controller;

import com.korgun.springcourse.model.Person;
import com.korgun.springcourse.repositories.PeopleRepository;
import com.korgun.springcourse.services.PeopleService;
import com.korgun.springcourse.util.PersonValidator;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/people")
public class PeopleController {

    private final PeopleService peopleService;
    private final PersonValidator personValidator;

    public PeopleController(PeopleService peopleService, PersonValidator personValidator) {
        this.peopleService = peopleService;
        this.personValidator = personValidator;
    }

    @GetMapping
    public String indexPerson(Model model){
        model.addAttribute("people", peopleService.findAll());
        return "people/index";
    }

    @GetMapping("/{id}")
    public String showPerson(@PathVariable("id") int userId, Model model){
        model.addAttribute("person", peopleService.findOne(userId));
        model.addAttribute("library", peopleService.getBooksByUserId(userId));
        return "people/show";
    }

    @GetMapping("/new")
    public String newPerson(Model model){
        model.addAttribute("person", new Person());
        return "people/new";
    }

    @PostMapping()
    public String createPerson(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult){
        personValidator.validate(person, bindingResult);
        if(bindingResult.hasErrors())
            return "people/new";
        peopleService.savePerson(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String editPerson(@PathVariable("id") int userId, Model model){
        model.addAttribute("person", peopleService.findOne(userId));
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String updatePerson(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult,
                               @PathVariable("id") int userId){
        personValidator.validate(person, bindingResult);
        if(bindingResult.hasErrors()) return "people/edit";

        peopleService.updatePerson(userId, person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String deletePerson(@PathVariable("id") int userId){
        peopleService.deletePerson(userId);
        return "redirect:/people";
    }
}
