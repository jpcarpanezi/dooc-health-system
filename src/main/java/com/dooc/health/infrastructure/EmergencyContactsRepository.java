package com.dooc.health.infrastructure;

import com.dooc.health.models.EmergencyContacts;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmergencyContactsRepository extends CrudRepository<EmergencyContacts, Integer> {
	@Query(value = "SELECT * FROM EmergencyContacts WHERE idPerson = ?1", nativeQuery = true)
	EmergencyContacts findByPersonId(int id);
}
