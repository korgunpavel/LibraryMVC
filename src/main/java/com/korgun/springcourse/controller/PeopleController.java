package com.korgun.springcourse.controller;

import com.korgun.springcourse.dao.PersonDAO;
import com.korgun.springcourse.model.Person;
import com.korgun.springcourse.util.PersonValidator;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/people")
public class PeopleController {

    private final PersonDAO personDAO;
    private final PersonValidator personValidator;

    public PeopleController(PersonDAO personDAO, PersonValidator personValidator) {
        this.personDAO = personDAO;
        this.personValidator = personValidator;
    }

    @GetMapping
    public String indexPerson(Model model){
        model.addAttribute("people", personDAO.indexPerson());
        return "people/index";
    }

    @GetMapping("/{id}")
    public String showPerson(@PathVariable("id") int user_id, Model model){
        model.addAttribute("person", personDAO.showPerson(user_id));
        model.addAttribute("library", personDAO.getBooksBy(user_id));
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
        if(bindingResult.hasErrors()) return "people/new";

        personDAO.savePerson(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String editPerson(@PathVariable("id") int user_id, Model model){
        model.addAttribute("person", personDAO.showPerson(user_id));
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String updatePerson(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult,
                               @PathVariable("id") int user_id){
        personValidator.validate(person, bindingResult);
        if(bindingResult.hasErrors()) return "people/edit";

        personDAO.updatePerson(user_id, person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String deletePerson(@PathVariable("id") int user_id){
        personDAO.deletePerson(user_id);
        return "redirect:/people";
    }
}
