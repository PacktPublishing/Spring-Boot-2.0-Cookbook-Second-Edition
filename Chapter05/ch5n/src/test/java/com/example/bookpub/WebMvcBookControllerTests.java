package com.example.bookpub;

import com.example.bookpub.entity.Author;
import com.example.bookpub.entity.Book;
import com.example.bookpub.entity.Publisher;
import com.example.bookpub.repository.AuthorRepository;
import com.example.bookpub.repository.BookRepository;
import com.example.bookpub.repository.PublisherRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest
public class WebMvcBookControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookRepository repository;

    // The 2 repositories below are needed to
    //successfully initialize StartupRunner
    @MockBean
    private AuthorRepository authorRepository;
    @MockBean
    private PublisherRepository publisherRepository;

    @Test
    public void webappBookApi() throws Exception {
        given(repository.findBookByIsbn("978-1-78528-415-1"))
                .willReturn(new Book("978-1-78528-415-1", "Spring Boot Recipes",
                        new Author("Alex", "Antonov"),
                        new Publisher("Packt")));

        mockMvc.perform(get("/books/978-1-78528-415-1")).
                andExpect(status().isOk()).
                andExpect(content().
                        contentType(MediaType.parseMediaType("application/json;charset=UTF-8"))).
                andExpect(content().string(containsString("Spring Boot Recipes"))).
                andExpect(jsonPath("$.isbn").value("978-1-78528-415-1"));
    }
}
