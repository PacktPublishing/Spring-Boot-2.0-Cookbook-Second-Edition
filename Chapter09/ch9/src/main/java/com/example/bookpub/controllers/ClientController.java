package com.example.bookpub.controllers;

import com.example.bookpub.api.BookPubClient;
import com.example.bookpub.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private BookPubClient client;

    @RequestMapping(value = "/book/{isbn}", method = RequestMethod.GET)
    public Book getBook(@PathVariable String isbn) {
        return client.findBookByIsbn(isbn);
    }
}
