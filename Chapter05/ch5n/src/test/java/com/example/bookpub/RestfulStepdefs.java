package com.example.bookpub;
import com.example.bookpub.repository.BookRepository;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

//@WebAppConfiguration
//@ContextConfiguration(classes = BookPubApplication.class, loader = SpringBootContextLoader.class)
public class RestfulStepdefs {
    @Autowired
    private WebApplicationContext context;
    @Autowired
    private BookRepository bookRepository;

    private MockMvc mockMvc;
    private ResultActions result;

    @Before
    public void setup() throws IOException {
        mockMvc =
                MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Given("^catalogue with books$")
    public void catalogue_with_books() {
        assertTrue(bookRepository.count() > 0);
    }

    @When("^requesting url ([^\"]*)$")
    public void requesting_url(String url) throws Exception {
        result = mockMvc.perform(get(url));
    }

    @Then("^status code will be ([\\d]*)$")
    public void status_code_will_be(int code) throws
            Throwable {
        assertNotNull(result);
        result.andExpect(status().is(code));
    }

    @Then("^response content contains ([^\"]*)$")
    public void response_content_contains(String content)
            throws Throwable {
        assertNotNull(result);
        result.andExpect(content().string(containsString(content)));
    }
}
