package com.korgun.springcourse.services;

import com.korgun.springcourse.model.Book;
import com.korgun.springcourse.model.Person;
import com.korgun.springcourse.repositories.LibraryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class LibraryService {

    private final LibraryRepository libraryRepository;

    public LibraryService(LibraryRepository libraryRepository) {
        this.libraryRepository = libraryRepository;
    }

    public List<Book> findAll (boolean sort){
        if(sort)
            return libraryRepository.findAll(Sort.by("year"));
        else
            return libraryRepository.findAll();
    }

    public Page<Book> findPagination (int page, int book_per_page, boolean sort){
        if(sort)
            return libraryRepository.findAll(PageRequest.of(page, book_per_page, Sort.by("year")));
        else
            return libraryRepository.findAll(PageRequest.of(page, book_per_page));
    }

    public Book findOne(int bookId){
        Optional<Book> findOne = libraryRepository.findById(bookId);
        return findOne.orElse(null);
    }

    public Person getNameWhoTake(int bookId){
        return libraryRepository.getReferenceById(bookId).getOwner();
    }

    public List<Book> searchByTitle(String text){
        return libraryRepository.findByTitleStartingWith(text);
    }

    @Transactional
    public void saveBook(Book book){
        libraryRepository.save(book);
    }

    @Transactional
    public void updateBook(int bookId, Book updatedBook){
        updatedBook.setBookId(bookId);
        updatedBook.setOwner(libraryRepository.getReferenceById(bookId).getOwner());
        libraryRepository.save(updatedBook);
    }

    @Transactional
    public void deleteBook(int bookId){
        libraryRepository.deleteById(bookId);
    }

    @Transactional
    public void assignBook(int bookId, Person person){
        libraryRepository.getReferenceById(bookId).setOwner(person);
        libraryRepository.getReferenceById(bookId).setTakenAt(new Date());
    }

    @Transactional
    public void releaseBook(int bookId){
        libraryRepository.getReferenceById(bookId).setOwner(null);
        libraryRepository.getReferenceById(bookId).setTakenAt(null);
    }
}
