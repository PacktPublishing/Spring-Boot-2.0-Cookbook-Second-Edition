package com.example.bookpub;

import com.example.bookpub.entity.Book;
import com.example.bookpub.repository.BookRepository;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import javax.sql.DataSource;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@Transactional
//@Sql(scripts = "classpath:/test-data.sql")
public class BookPubApplicationTests {
	@Autowired
	private WebApplicationContext context;
	@Autowired
	private TestRestTemplate restTemplate;
	@Autowired
	private BookRepository repository;
	@Autowired
	private DataSource ds;

	@LocalServerPort
	private int port;

	private MockMvc mockMvc;
	private static boolean loadDataFixtures = true;

	@Before
	public void setupMockMvc() {
		mockMvc = webAppContextSetup(context).build();
	}

	@Before
	public void loadDataFixtures() {
		if (loadDataFixtures) {
			ResourceDatabasePopulator populator = new ResourceDatabasePopulator(context.getResource("classpath:/test-data.sql"));
			DatabasePopulatorUtils.execute(populator, ds);
			loadDataFixtures = false;
		}
	}


	@Test
	public void contextLoads() {
		assertEquals(3, repository.count());
	}

	@Test
	public void webappBookIsbnApi() {
		Book book =
            restTemplate.getForObject("/books/978-1-78528-415-1", Book.class);
		assertNotNull(book);
		assertEquals("Packt", book.getPublisher().getName());
	}

	@Test
	public void webappPublisherApi() throws Exception {
		mockMvc.perform(get("/publishers/1")).
				andExpect(status().isOk()).
                andExpect(content().
				    contentType(MediaType.parseMediaType("application/hal+json;charset=UTF-8"))).
				andExpect(content().string(containsString("Packt"))).
				andExpect(jsonPath("$.name").value("Packt"));
	}
}
