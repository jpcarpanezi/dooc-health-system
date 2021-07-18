package com.dooc.health.infrastructure;

import com.dooc.health.models.MedicalInformations;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicalInformationsRepository extends CrudRepository<MedicalInformations, Integer> {
	MedicalInformations findMedicalInformationsByPersonEmail(String email);
	MedicalInformations findMedicalInformationsByPersonCpf(String cpf);
	MedicalInformations findMedicalInformationsByPersonId(int personId);
}
