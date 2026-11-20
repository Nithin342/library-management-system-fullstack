package com.library.managament.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.library.managament.entity.Author;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

}
