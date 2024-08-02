package com.korgun.springcourse.services;

import com.korgun.springcourse.model.Book;
import com.korgun.springcourse.model.Person;
import com.korgun.springcourse.repositories.PeopleRepository;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@Transactional(readOnly = true)
public class PeopleService {

    private final PeopleRepository peopleRepository;

    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public List<Person> findAll(){
        return peopleRepository.findAll();
    }

    public Person findOne(int userId){
        Optional<Person> findOne =  peopleRepository.findById(userId);

        return findOne.orElse(null);
    }

    @Transactional
    public void savePerson(Person person){
        peopleRepository.save(person);
    }

    @Transactional
    public void updatePerson(int userId, Person updatedPerson){
        updatedPerson.setUserId(userId);
        peopleRepository.save(updatedPerson);
    }

    @Transactional
    public void deletePerson(int userId){
        peopleRepository.deleteById(userId);
    }

    public List<Book> getBooksByUserId(int userId){
        Optional<Person> person = peopleRepository.findById(userId);

        if(person.isPresent()){
            Hibernate.initialize(person.get().getBooks());
            List<Book> books = person.get().getBooks();
            for(Book book:books){
                long diff = book.getTakenAt().getTime() - new Date().getTime();
                if(diff> TimeUnit.SECONDS.toMillis(10)){
                    book.setExp(true);
                }
            }
            return person.get().getBooks();
        } else {
            return Collections.emptyList();
        }
    }

    public Optional<Person> getPersonByFullName(String fullName){
        return peopleRepository.findByFullName(fullName);
    }

}
