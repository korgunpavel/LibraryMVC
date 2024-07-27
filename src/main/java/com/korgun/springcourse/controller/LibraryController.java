package com.korgun.springcourse.controller;

import com.korgun.springcourse.dao.BookDAO;
import com.korgun.springcourse.dao.PersonDAO;
import com.korgun.springcourse.model.Book;
import com.korgun.springcourse.model.Person;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/library")
public class LibraryController {

    private final PersonDAO personDAO;
    private final BookDAO bookDAO;

    public LibraryController(PersonDAO personDAO, BookDAO bookDAO) {
        this.personDAO = personDAO;
        this.bookDAO = bookDAO;
    }

    @GetMapping()
    public String indexBook(Model model){
        model.addAttribute("library",bookDAO.indexBook());
        return "library/index";
    }

    @GetMapping("/{id}")
    public String showBook(@PathVariable("id") int book_id, Model model, @ModelAttribute("person") Person person){
        model.addAttribute("book", bookDAO.showBook(book_id));

        Optional<Person> bookOwner = bookDAO.getNameWhoTake(book_id);

        if(bookOwner.isPresent()) model.addAttribute("owner", bookOwner.get());
        else model.addAttribute("people", personDAO.indexPerson());

        return "library/show";
    }

    @GetMapping("/new")
    public String newBook(Model model){
        model.addAttribute("book", new Book());
        return "library/new";
    }

    @PostMapping()
    public String createBook(@ModelAttribute("book") @Valid Book book){
        bookDAO.saveBook(book);
        return "redirect:/library";
    }

    @GetMapping("/{id}/edit")
    public String editBook(@PathVariable("id") int book_id, Model model){
        model.addAttribute("book", bookDAO.showBook(book_id));
        return "library/edit";
    }

    @PatchMapping("/{id}")
    public String updateBook(@ModelAttribute("book") @Valid Book book, @PathVariable("id") int book_id){
        bookDAO.updateBook(book_id, book);
        return "redirect:/library";
    }

    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable("id") int book_id){
        bookDAO.deleteBook(book_id);
        return "redirect:/library";
    }

    @PatchMapping("/{id}/assign")
    public String assign(@ModelAttribute("person") Person person, @PathVariable("id") int book_id){
        bookDAO.assignBook(book_id, person);
        return "redirect:/library/" + book_id;
    }

    @PatchMapping("/{id}/release")
    public String release(@PathVariable("id") int book_id){
        bookDAO.releaseBook(book_id);
        return "redirect:/library/" + book_id;
    }
}
