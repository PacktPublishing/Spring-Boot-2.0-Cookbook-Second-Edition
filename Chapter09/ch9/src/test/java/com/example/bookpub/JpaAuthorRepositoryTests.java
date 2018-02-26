package com.example.bookpub;

import com.example.bookpub.api.BookPubClient;
import com.example.bookpub.entity.Author;
import com.example.bookpub.repository.AuthorRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class JpaAuthorRepositoryTests {
    @Autowired
    private TestEntityManager mgr;

    @Autowired
    private AuthorRepository repository;

    @MockBean
    private BookPubClient client;

    @Test
    public void testAuthorEntityBinding() {
        Long id = mgr.persistAndGetId(createAuthor(), Long.class);

        Author author = repository.findById(id).get();

        assertThat(author.getFirstName()).isEqualTo("Mark");
        assertThat(author.getLastName()).isEqualTo("Twain");
    }

    private Author createAuthor() {
        return new Author("Mark", "Twain");
    }
}
