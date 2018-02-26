package com.example.bookpub.controllers;

import com.example.bookpub.entity.Book;
import com.example.bookpub.entity.Publisher;
import com.example.bookpub.entity.Reviewer;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import com.example.bookpub.editors.IsbnEditor;
import com.example.bookpub.model.Isbn;
import com.example.bookpub.repository.BookRepository;
import com.example.bookpub.repository.PublisherRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Optional;

@RestController
@RequestMapping("/books")
public class BookController {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private PublisherRepository publisherRepository;

    @RequestMapping(value = "/publisher/{id}", method = RequestMethod.GET)
    public List<Book> getBooksByPublisher(@PathVariable("id") Long id) {
        Optional<Publisher> publisher = publisherRepository.findById(id);
        Assert.notNull(publisher);
        Assert.isTrue(publisher.isPresent());
        return publisher.get().getBooks();
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Isbn.class, new IsbnEditor());
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    @HystrixCommand(fallbackMethod = "getEmptyBooksList")
    public Iterable<Book> getAllBooks() {
        Random random = new Random();
        if (random.nextBoolean())
            return bookRepository.findAll();
        else
            throw new RuntimeException("Books Service Not Available");
    }

    public Iterable<Book> getEmptyBooksList() {
        return Collections.emptyList();
    }

    @RequestMapping(value = "/{isbn}", method = RequestMethod.GET)
    public Book getBook(@PathVariable Isbn isbn) {
        return bookRepository.findBookByIsbn(isbn.toString());
    }

    @RequestMapping(value = "/{isbn}/reviewers", method = RequestMethod.GET)
    public List<Reviewer> getReviewers(@PathVariable("isbn") Book book) {
        return book.getReviewers();
    }

    @RequestMapping(value = "/session", method = RequestMethod.GET)
    public String getSessionId(HttpServletRequest request) {
        return request.getSession().getId();
    }
}
