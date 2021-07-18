package com.dooc.health.infrastructure;

import com.dooc.health.models.EmergencyContacts;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmergencyContactsRepository extends JpaRepository<EmergencyContacts, Integer> {
	Page<EmergencyContacts> findEmergencyContactsByPersonEmail(String email, Pageable pageable);
	Page<EmergencyContacts> findEmergencyContactsByPersonCpf(String cpf, Pageable pageable);
	Page<EmergencyContacts> findEmergencyContactsByPersonId(int id, Pageable pageable);
	EmergencyContacts getEmergencyContactsByIdPerson(int id);
}
