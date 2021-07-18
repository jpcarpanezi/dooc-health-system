package com.dooc.health.infrastructure;

import com.dooc.health.models.MedicalConsultation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicalConsultationRepository extends CrudRepository<MedicalConsultation, Integer> {
    Iterable<MedicalConsultation> getMedicalConsultationByIdPerson(int personID);
}
