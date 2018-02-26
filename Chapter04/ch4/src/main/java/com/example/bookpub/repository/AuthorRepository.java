package com.example.bookpub.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import com.example.bookpub.entity.Author;

@RepositoryRestResource(collectionResourceRel = "writers", path = "writers")
//@RepositoryRestResource
public interface AuthorRepository extends PagingAndSortingRepository<Author, Long> {
}
