package com.korgun.springcourse.dao;

import com.korgun.springcourse.model.Book;
import com.korgun.springcourse.model.Person;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class BookDAO {

    private final JdbcTemplate jdbcTemplate;

    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> indexBook(){
        return jdbcTemplate.query("SELECT * FROM Book", new BeanPropertyRowMapper<>(Book.class));
    }

    public Book showBook(int book_id){
        return jdbcTemplate.query("SELECT * FROM Book WHERE book_id=?", new Object[]{book_id}
                , new BeanPropertyRowMapper<>(Book.class)).stream().findAny().orElse(null);
    }
    public Optional<Person> getNameWhoTake(int book_id) {
        return jdbcTemplate.query("SELECT * FROM Book JOIN Person ON Book.user_id = Person.user_id " +
                        "WHERE book_id=?", new Object[]{book_id}
                , new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
    }

    public void saveBook(Book book){
        jdbcTemplate.update("INSERT INTO Book(bookName, author, bookYear) VALUES (?,?,?)"
                ,book.getBookName(), book.getAuthor(), book.getBookYear());
    }

    public void updateBook(int book_id, Book updatedBook){
        jdbcTemplate.update("UPDATE Book SET bookName=?, author=?, bookYear=? WHERE book_id-?"
                ,updatedBook.getBookName(), updatedBook.getAuthor(), updatedBook.getBookYear(), book_id);
    }

    public void deleteBook(int book_id){
        jdbcTemplate.update("DELETE FROM Person WHERE id=?", book_id);
    }

    public void assignBook(int book_id, Person person){
        jdbcTemplate.update("UPDATE Book SET user_id=? WHERE book_id=?", person.getUser_id(), book_id);
    }

    public void releaseBook(int book_id){
        jdbcTemplate.update("UPDATE Book Set user_id=null WHERE book_id=?", book_id);
    }
}
