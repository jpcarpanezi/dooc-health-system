package com.dooc.health.controllers;

import com.dooc.health.infrastructure.MedicalInformationsRepository;
import com.dooc.health.models.MedicalInformations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientResponseException;

@RestController
@RequestMapping("/medicalinformations")
public class MedicalInformationsController {
	private final MedicalInformationsRepository medicalInformationsRepository;

	public MedicalInformationsController(MedicalInformationsRepository medicalInformationsRepository) {
		this.medicalInformationsRepository = medicalInformationsRepository;
	}

	// POST /medicalinformations
	@PostMapping
	public @ResponseStatus ResponseEntity<MedicalInformations> insertMedicalInformation(@RequestBody MedicalInformations medicalInformations) throws RestClientResponseException {
		MedicalInformations medicalInformationsInsert = new MedicalInformations();
		medicalInformationsInsert.setId(medicalInformations.getId());
		medicalInformationsInsert.setBloodType(medicalInformations.getBloodType());
		medicalInformationsInsert.setMedicalConditions(medicalInformations.getMedicalConditions());
		medicalInformationsInsert.setAllergies(medicalInformations.getAllergies());
		medicalInformationsInsert.setObservations(medicalInformations.getObservations());

		medicalInformationsRepository.save(medicalInformationsInsert);
		return new ResponseEntity<MedicalInformations>(medicalInformationsInsert, HttpStatus.CREATED);
	}

	// GET medicalinformations?getMethod=email&value=email@example.com
	@GetMapping
	public @ResponseStatus ResponseEntity<MedicalInformations> getMedicalInformations(@RequestParam(defaultValue = "email") String getMethod, @RequestParam String value) throws RestClientResponseException {
		MedicalInformations medicalInformations = null;
		switch (getMethod) {
			case "email":
				medicalInformations = medicalInformationsRepository.findMedicalInformationsByPersonEmail(value);
				break;
			case "cpf":
				medicalInformations = medicalInformationsRepository.findMedicalInformationsByPersonCpf(value);
				break;
			case "id":
				medicalInformations = medicalInformationsRepository.findMedicalInformationsByPersonId(Integer.parseInt(value));
				break;
			default:
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		if (medicalInformations == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<MedicalInformations>(medicalInformations, HttpStatus.OK);
	}

	// PUT medicalinformations?updateMethod=email&value=email@example.com
	@PutMapping
	public @ResponseStatus ResponseEntity<MedicalInformations> updateMedicalInformations(@RequestBody MedicalInformations medicalInformations, @RequestParam(defaultValue = "email") String updateMethod, @RequestParam String value) throws RestClientResponseException {
		MedicalInformations medicalInformationsUpdate = null;
		switch (updateMethod) {
			case "email":
				medicalInformationsUpdate = medicalInformationsRepository.findMedicalInformationsByPersonEmail(value);
				break;
			case "cpf":
				medicalInformationsUpdate = medicalInformationsRepository.findMedicalInformationsByPersonCpf(value);
				break;
			case "id":
				medicalInformationsUpdate = medicalInformationsRepository.findMedicalInformationsByPersonId(Integer.parseInt(value));
				break;
			default:
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		if (medicalInformationsUpdate == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		medicalInformationsUpdate.setBloodType(medicalInformations.getBloodType());
		medicalInformationsUpdate.setMedicalConditions(medicalInformations.getMedicalConditions());
		medicalInformationsUpdate.setAllergies(medicalInformations.getAllergies());
		medicalInformationsUpdate.setObservations(medicalInformations.getObservations());

		medicalInformationsRepository.save(medicalInformationsUpdate);

		return new ResponseEntity<MedicalInformations>(medicalInformationsUpdate, HttpStatus.OK);
	}
}
