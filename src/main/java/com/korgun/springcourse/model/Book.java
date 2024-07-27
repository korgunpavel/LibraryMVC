package com.korgun.springcourse.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public class Book {
    private int book_id;

    @NotEmpty(message = "Name of book should be not empty")
    private String bookName;

    @NotEmpty(message = "Author name should be not empty")
    private String author;

    @Min(value = 1500, message = "Год должен быть больше, чем 1500")
    private int bookYear;

    public Book() {
    }

    public Book(int book_id, String bookName, String author, int bookYear) {
        this.book_id = book_id;
        this.bookName = bookName;
        this.author = author;
        this.bookYear = bookYear;
    }

    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getBookYear() {
        return bookYear;
    }

    public void setBookYear(int bookYear) {
        this.bookYear = bookYear;
    }
}
