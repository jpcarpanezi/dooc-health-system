package com.dooc.health.infrastructure;

import com.dooc.health.models.Person;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends CrudRepository<Person, Integer> {
	@Query(value = "SELECT * FROM Person WHERE email = ?1", nativeQuery = true)
	Person findByEmail(String email);
}
