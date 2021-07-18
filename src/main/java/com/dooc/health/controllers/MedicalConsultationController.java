package com.dooc.health.controllers;

import com.dooc.health.infrastructure.MedicalConsultationRepository;
import com.dooc.health.models.MedicalConsultation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientResponseException;

import java.util.Optional;

@RestController
@RequestMapping("/medicalconsultation")
public class MedicalConsultationController {
	private final MedicalConsultationRepository medicalConsultationRepository;

	public MedicalConsultationController(MedicalConsultationRepository medicalConsultationRepository) {
		this.medicalConsultationRepository = medicalConsultationRepository;
	}

	// POST /medicalconsultation
	@PostMapping
	public @ResponseStatus ResponseEntity<MedicalConsultation> insertMedicalConsultation(@RequestBody MedicalConsultation consultation) throws RestClientResponseException {
		MedicalConsultation consultation1 = new MedicalConsultation();

		consultation1.setConsultationDate(consultation.getConsultationDate());
		consultation1.setReason(consultation.getReason());
		consultation1.setDiagnose(consultation.getDiagnose());
		consultation1.setObservations(consultation.getObservations());
		consultation1.setIdPerson(consultation.getIdPerson());

		medicalConsultationRepository.save(consultation1);

		return new ResponseEntity<MedicalConsultation>(consultation, HttpStatus.CREATED);
	}

	// GET /medicalconsultation/getByID
	@GetMapping("/getByID")
	public @ResponseStatus ResponseEntity<MedicalConsultation> getMedicalConsultationByID(@RequestParam int id) throws RestClientResponseException {
		Optional<MedicalConsultation> consultation = medicalConsultationRepository.findById(id);

		return new ResponseEntity<>(consultation.orElse(null), HttpStatus.OK);
	}

	// GET /medicalconsultation/getAll
	@GetMapping("/getAll")
	public @ResponseStatus ResponseEntity<Iterable<MedicalConsultation>> getMedicalConsultation(@RequestParam int personID) throws RestClientResponseException {
		Iterable<MedicalConsultation> consultations = medicalConsultationRepository.getMedicalConsultationByIdPerson(personID);

		return new ResponseEntity<>(consultations, HttpStatus.OK);
	}

	// PATCH /medicalconsultation/
	@PatchMapping
	public @ResponseStatus ResponseEntity<MedicalConsultation> patchMedicalConsultation(@RequestBody MedicalConsultation consultation) throws RestClientResponseException {
		Optional<MedicalConsultation> consultationOptional = medicalConsultationRepository.findById(consultation.getID());
		if (consultationOptional.isEmpty()){
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		MedicalConsultation updateConsultation = consultationOptional.get();

		updateConsultation.setConsultationDate(consultation.getConsultationDate());
		updateConsultation.setReason(consultation.getReason());
		updateConsultation.setDiagnose(consultation.getDiagnose());
		updateConsultation.setObservations(consultation.getObservations());
		updateConsultation.setIdPerson(consultation.getIdPerson());

		medicalConsultationRepository.save(updateConsultation);

		return new ResponseEntity<>(updateConsultation, HttpStatus.OK);
	}
}
