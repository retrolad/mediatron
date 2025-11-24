package com.retrolad.mediatron.repository;

import com.retrolad.mediatron.model.movie.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
}
