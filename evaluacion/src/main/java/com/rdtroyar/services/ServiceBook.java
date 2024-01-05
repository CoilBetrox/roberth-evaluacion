package com.rdtroyar.services;

import com.rdtroyar.db.Book;

import java.util.List;

public interface ServiceBook {
    List<Book> findAll();
    void insert(Book b);
    Book findById(Integer id);
    void update(Book b);
    boolean delete(Integer id);
}
