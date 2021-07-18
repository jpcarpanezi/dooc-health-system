package com.dooc.health.infrastructure;

import com.dooc.health.models.Medicines;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
public interface MedicinesRepository extends JpaRepository<Medicines, Integer>{



    Medicines getMedicinesByDrugName(String drugName);

    Medicines getMedicinesByID(int idMedicines);

    Medicines getMedicinesByActiveIngredientLike(String activeIngredient);
}






