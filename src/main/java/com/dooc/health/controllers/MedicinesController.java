package com.dooc.health.controllers;

import com.dooc.health.infrastructure.MedicinesRepository;
import com.dooc.health.models.Medicines;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientResponseException;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/medicines")
public class MedicinesController {
	private final MedicinesRepository medicinesRepository;

	public MedicinesController(MedicinesRepository medicinesRepository) {
		this.medicinesRepository = medicinesRepository;
	}

	@PostMapping
	public @ResponseStatus ResponseEntity<Medicines> insertMedicines(@RequestBody Medicines medicines) throws RestClientResponseException{
		Medicines insertMedicines = new Medicines();
		insertMedicines.setDrugName(medicines.getDrugName());
		insertMedicines.setActiveIngredient(medicines.getActiveIngredient());
		insertMedicines.setFormRoute(medicines.getFormRoute());
		insertMedicines.setCompany(medicines.getCompany());

		medicinesRepository.save(insertMedicines);
		return  new ResponseEntity<Medicines>(medicines, HttpStatus.CREATED);
	}

	@GetMapping
	public @ResponseStatus ResponseEntity<Map<String, Object>> getAllMedicines(@RequestParam(defaultValue = "0") int page, @RequestParam int limit) throws RestClientResponseException {
		if(limit > 50) limit = 50;

		Page<Medicines> medicines = medicinesRepository.findAll(PageRequest.of(page, limit, Sort.by("drugName").ascending()));

		Map<String, Object> response = new HashMap<>();
		response.put("medicines", medicines);
		response.put("currentPage", medicines.getNumber());
		response.put("totalItems", medicines.getTotalElements());
		response.put("totalPages", medicines.getTotalPages());

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/medicamento")
	public @ResponseStatus ResponseEntity<Medicines> getMedicineByParam(@RequestParam(defaultValue = "drugName") String selectBy, @RequestParam(required = false) String drugName, @RequestParam(required = false) String activeIngredient) throws RestClientResponseException {
		Medicines medicines = null;
		if(selectBy.equals("drugName") && drugName != null) {
			medicines = medicinesRepository.getMedicinesByDrugName(drugName);
		} else if(selectBy.equals("activeIngredient") && activeIngredient!= null) {
			medicines = medicinesRepository.getMedicinesByActiveIngredientLike('%'+activeIngredient+'%');
		}

		if (medicines == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<Medicines>(medicines, HttpStatus.OK);
	}

	@PutMapping
	public @ResponseStatus ResponseEntity<Medicines> updateMedicines(@RequestBody Medicines medicines, @RequestParam(defaultValue = "drugName") String updateMethod, @RequestParam(required = false) String drugName, @RequestParam(required = false) String activeIngredient) throws RestClientResponseException {
		Medicines updateMedicines = null;
		if (updateMethod.equals("drugName")) {
			updateMedicines = medicinesRepository.getMedicinesByDrugName(drugName);
		} else if(updateMethod.equals("activeIngredient")) {
			updateMedicines = medicinesRepository.getMedicinesByActiveIngredientLike(activeIngredient);
		}

		if (updateMedicines == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		updateMedicines.setDrugName(medicines.getDrugName());
		updateMedicines.setActiveIngredient(medicines.getActiveIngredient());
		updateMedicines.setFormRoute(medicines.getFormRoute());
		updateMedicines.setCompany(medicines.getCompany());

		medicinesRepository.save(updateMedicines);
		return new ResponseEntity<Medicines>(updateMedicines, HttpStatus.OK);
	}
}

