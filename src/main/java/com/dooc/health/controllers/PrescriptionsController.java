package com.dooc.health.controllers;

import com.dooc.health.infrastructure.PrescriptionsRepository;
import com.dooc.health.models.MedicalConsultation;
import com.dooc.health.models.Prescriptions;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/prescriptions")
public class PrescriptionsController {
	private final PrescriptionsRepository prescriptionsRepository;

	public PrescriptionsController(PrescriptionsRepository prescriptionsRepository) {
		this.prescriptionsRepository = prescriptionsRepository;
	}

	// POST /prescriptions
	@PostMapping
	public @ResponseStatus ResponseEntity<Prescriptions> insertPrescription(@RequestBody Prescriptions prescription){
		Prescriptions prescription1 = new Prescriptions();

		prescription1.setIdPerson(prescription.getIdPerson());
		prescription1.setIdConsultation(prescription.getIdConsultation());
		prescription1.setIdMedicine(prescription.getIdMedicine());
		prescription1.setDosage(prescription.getDosage());

		prescriptionsRepository.save(prescription1);

		return new ResponseEntity<>(prescription1, HttpStatus.CREATED);
	}

	// PATCH /prescriptions
	@PatchMapping
	public @ResponseStatus ResponseEntity<Prescriptions> patchPrescription(@RequestBody Prescriptions prescriptionUpdate){
		Optional<Prescriptions> prescriptionsOptional = prescriptionsRepository.findById(prescriptionUpdate.getID());
		if (prescriptionsOptional.isEmpty()){
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		Prescriptions prescription = prescriptionsOptional.get();

		prescription.setIdPerson(prescriptionUpdate.getIdPerson());
		prescription.setIdConsultation(prescriptionUpdate.getIdConsultation());
		prescription.setIdMedicine(prescriptionUpdate.getIdMedicine());
		prescription.setDosage(prescriptionUpdate.getDosage());

		prescriptionsRepository.save(prescription);

		return new ResponseEntity<>(prescription, HttpStatus.OK);
	}

	// GET /prescriptions?id=1
	@GetMapping
	public @ResponseStatus ResponseEntity<Prescriptions> getPrescription(@RequestParam int id){
		Optional<Prescriptions> prescription = prescriptionsRepository.findById(id);

		if (prescription.isEmpty()){
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(prescription.get(), HttpStatus.OK);
	}


	// GET /prescriptions/consultation?idConsultation=1
	@GetMapping("consultation")
	public @ResponseStatus ResponseEntity<Iterable<Prescriptions>> getPrescriptionByConsultation(@RequestParam int idConsultation){
		Iterable<Prescriptions> prescriptions = prescriptionsRepository.getPrescriptionsByIdConsultation(idConsultation);

		if (!prescriptions.iterator().hasNext()){
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(prescriptions, HttpStatus.OK);
	}
}
