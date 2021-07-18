package com.dooc.health.infrastructure;

import com.dooc.health.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {
	Person getPersonByEmail(String email);
	Person getPersonByCpf(String cpf);
}
