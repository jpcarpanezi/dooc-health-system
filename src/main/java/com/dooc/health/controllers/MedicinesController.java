package com.dooc.health.controllers;

import com.dooc.health.infrastructure.MedicinesRepository;
import com.dooc.health.models.Medicines;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientResponseException;

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


	@GetMapping("/medicamento")
	public @ResponseStatus ResponseEntity<Medicines> getPersonByParam(@RequestParam(defaultValue = "drugName") String selectBy, @RequestParam(required = false) String drugName, @RequestParam(required = false) String activeIngredient) throws RestClientResponseException {
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

