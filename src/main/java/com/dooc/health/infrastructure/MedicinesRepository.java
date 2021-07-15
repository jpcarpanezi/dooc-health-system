package com.dooc.health.infrastructure;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicinesRepository extends CrudRepository<MedicinesRepository, Integer> {
}
