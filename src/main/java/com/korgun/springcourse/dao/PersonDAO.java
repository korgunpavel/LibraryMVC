package com.korgun.springcourse.dao;

import com.korgun.springcourse.model.Book;
import com.korgun.springcourse.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class PersonDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> indexPerson(){
        return jdbcTemplate.query("SELECT * FROM Person", new BeanPropertyRowMapper<>(Person.class));
    }

    public Person showPerson(int user_id){
        return jdbcTemplate.query("SELECT * FROM Person WHERE user_id=?", new Object[]{user_id}
                        , new BeanPropertyRowMapper<>(Person.class)).stream().findAny().orElse(null);
    }
    public Optional<Person> showPersonName(String fullName){
        return jdbcTemplate.query("SELECT * FROM Person WHERE fullName=?", new Object[]{fullName}
                        , new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
    }

    public List<Book> getBooksBy(int user_id) {
        return jdbcTemplate.query("SELECT * FROM Book WHERE user_id=?", new Object[]{user_id}
        , new BeanPropertyRowMapper<>(Book.class));
    }

    public void savePerson(Person person){
        jdbcTemplate.update("INSERT INTO Person(fullName, yearOfBirth) VALUES(?,?)"
                ,person.getFullName(), person.getYearOfBirth());
    }

    public void updatePerson(int user_id, Person updatedPerson){
        jdbcTemplate.update("UPDATE Person SET fullName=?, yearOfBirth=? WHERE user_id=?"
                , updatedPerson.getFullName(),updatedPerson.getYearOfBirth(), user_id);
    }

    public void deletePerson(int user_id){
        jdbcTemplate.update("DELETE FROM Person WHERE id=?", user_id);
    }


}
