package com.yogeswari.librarymanagementsystem.repo;

import com.yogeswari.librarymanagementsystem.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepo extends JpaRepository<Book, Long> {

    public Book findBookByIsbn(String isbn);
    public List<Book> findByIsbnContainingIgnoreCase(String isbn);
    public List<Book> findBooksByTitleContainingIgnoreCase(String title);

}
