package com.dooc.health.controllers;

import com.dooc.health.infrastructure.MedicalInformationsRepository;
import com.dooc.health.models.MedicalInformations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/medicalinformations")
public class MedicalInformationsController {
	private final MedicalInformationsRepository medicalInformationsRepository;

	public MedicalInformationsController(MedicalInformationsRepository medicalInformationsRepository) {
		this.medicalInformationsRepository = medicalInformationsRepository;
	}

	@GetMapping("/{id}")
	public Iterable<MedicalInformations> GetMedicalInformationsByPersonID(@PathVariable int id) {
		return medicalInformationsRepository.findAll();
	}
}
