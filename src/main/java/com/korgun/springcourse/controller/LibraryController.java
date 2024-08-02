package com.korgun.springcourse.controller;

import com.korgun.springcourse.model.Book;
import com.korgun.springcourse.model.Person;
import com.korgun.springcourse.services.LibraryService;
import com.korgun.springcourse.services.PeopleService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/library")
public class LibraryController {

    private final PeopleService peopleService;
    private final LibraryService libraryService;

    public LibraryController(PeopleService peopleService, LibraryService libraryService) {
        this.peopleService = peopleService;
        this.libraryService = libraryService;
    }

    @GetMapping()
    public String indexBook(Model model, @RequestParam(value = "page", required = false) Integer page,
                            @RequestParam(value = "books_per_page", required = false) Integer books_per_page,
                            @RequestParam(value = "sort", required = false) boolean sort){
        if(page == null || books_per_page == null)
            model.addAttribute("library",libraryService.findAll(sort));
        else
            model.addAttribute("library",libraryService.findPagination(page, books_per_page, sort));

        return "library/index";
    }

    @GetMapping("/{id}")
    public String showBook(@PathVariable("id") int bookId, Model model, @ModelAttribute("person") Person person){
        model.addAttribute("book", libraryService.findOne(bookId));

        Optional<Person> bookOwner = Optional.ofNullable(libraryService.getNameWhoTake(bookId));

        if(bookOwner.isPresent()) model.addAttribute("owner", bookOwner.get());
        else model.addAttribute("people", peopleService.findAll());

        return "library/show";
    }

    @GetMapping("/new")
    public String newBook(Model model){
        model.addAttribute("book", new Book());
        return "library/new";
    }

    @PostMapping()
    public String createBook(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return "library/new";
        libraryService.saveBook(book);
        return "redirect:/library";
    }

    @GetMapping("/{id}/edit")
    public String editBook(@PathVariable("id") int bookId, Model model){
        model.addAttribute("book", libraryService.findOne(bookId));
        return "library/edit";
    }

    @PatchMapping("/{id}")
    public String updateBook(@ModelAttribute("book") @Valid Book book, @PathVariable("id") int bookId){
        libraryService.updateBook(bookId, book);
        return "redirect:/library";
    }

    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable("id") int bookId){
        libraryService.deleteBook(bookId);
        return "redirect:/library";
    }

    @PatchMapping("/{id}/assign")
    public String assign(@ModelAttribute("person") Person person, @PathVariable("id") int bookId){
        libraryService.assignBook(bookId, person);
        return "redirect:/library/" + bookId;
    }

    @PatchMapping("/{id}/release")
    public String release(@PathVariable("id") int bookId){
        libraryService.releaseBook(bookId);
        return "redirect:/library/" + bookId;
    }

    @GetMapping("/search")
    public String searchPage(){
        return "library/search";
    }

    @PostMapping("/search")
    public String searchBook(Model model, @RequestParam("text") String text){
        model.addAttribute("library",libraryService.searchByTitle(text));
        return "library/search";
    }
}
