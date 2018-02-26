package com.example.bookpub.formatters;

import com.example.bookpub.entity.Book;
import com.example.bookpub.repository.BookRepository;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.util.Locale;

public class BookFormatter implements Formatter<Book> {
    private BookRepository repository;

    public BookFormatter(BookRepository repository) {
        this.repository = repository;
    }

    @Override
    public Book parse(String bookIdentifier, Locale locale) throws ParseException {
        Book book = repository.findBookByIsbn(bookIdentifier);
        return book != null ? book : repository.findById(Long.valueOf(bookIdentifier)).get();
    }

    @Override
    public String print(Book book, Locale locale) {
        return book.getIsbn();
    }
}
