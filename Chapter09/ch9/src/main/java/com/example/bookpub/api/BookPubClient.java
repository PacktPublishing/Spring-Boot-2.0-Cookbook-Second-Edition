package com.example.bookpub.api;

import com.example.bookpub.entity.Book;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("http://BookPub-ch9")
public interface BookPubClient {
    @RequestMapping(path = "/books/{isbn}", method = RequestMethod.GET)
    public Book findBookByIsbn(@PathVariable("isbn") String isbn);
}
