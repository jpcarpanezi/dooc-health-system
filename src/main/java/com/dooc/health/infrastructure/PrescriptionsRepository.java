package com.dooc.health.infrastructure;

import com.dooc.health.models.Prescriptions;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrescriptionsRepository extends CrudRepository<Prescriptions, Integer> {
    Iterable<Prescriptions> getPrescriptionsByIdConsultation(int id);
}
