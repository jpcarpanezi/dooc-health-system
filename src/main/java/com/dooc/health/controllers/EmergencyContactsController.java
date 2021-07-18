package com.dooc.health.controllers;

import com.dooc.health.infrastructure.EmergencyContactsRepository;
import com.dooc.health.models.EmergencyContacts;
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
@RequestMapping("/emergencycontacts")
public class EmergencyContactsController {
	private final EmergencyContactsRepository emergencyContactsRepository;

	public EmergencyContactsController(EmergencyContactsRepository emergencyContactsRepository) {
		this.emergencyContactsRepository = emergencyContactsRepository;
	}

	// POST /emergencycontacts
	@PostMapping
	public @ResponseStatus ResponseEntity<EmergencyContacts> insertEmergencyContact(@RequestBody EmergencyContacts emergencyContacts) throws RestClientResponseException {
		EmergencyContacts emergencyContactsInsert = new EmergencyContacts();
		emergencyContactsInsert.setEmergencyName(emergencyContacts.getEmergencyName());
		emergencyContactsInsert.setPhone(emergencyContacts.getPhone());
		emergencyContactsInsert.setKinship(emergencyContacts.getKinship());
		emergencyContactsInsert.setIdPerson(emergencyContacts.getIdPerson());

		emergencyContactsRepository.save(emergencyContactsInsert);

		return new ResponseEntity<EmergencyContacts>(emergencyContactsInsert, HttpStatus.CREATED);
	}

	// PUT /emergencycontacts?id=1
	@PutMapping
	public @ResponseStatus ResponseEntity<EmergencyContacts> updateEmergencyContact(@RequestBody EmergencyContacts emergencyContacts, @RequestParam int id) throws RestClientResponseException {
		EmergencyContacts emergencyContactsUpdate = emergencyContactsRepository.getEmergencyContactsByIdPerson(id);

		if (emergencyContactsUpdate == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		emergencyContactsUpdate.setEmergencyName(emergencyContacts.getEmergencyName());
		emergencyContactsUpdate.setPhone(emergencyContacts.getPhone());
		emergencyContactsUpdate.setKinship(emergencyContacts.getKinship());

		emergencyContactsRepository.save(emergencyContactsUpdate);

		return new ResponseEntity<EmergencyContacts>(emergencyContactsUpdate, HttpStatus.OK);
	}

	// GET emergencycontacts?page=0&limit=10&getMethod=email&value=email@example.com
	@GetMapping
	public @ResponseStatus ResponseEntity<Map<String, Object>> getEmergencyContactsFromPerson(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int limit, @RequestParam(defaultValue = "email") String getMethod, @RequestParam String value) throws RestClientResponseException {
		if (limit > 50) limit = 50;

		Page<EmergencyContacts> emergencyContacts;
		PageRequest pageRequest =  PageRequest.of(page, limit, Sort.by("emergencyName").ascending());
		switch (getMethod) {
			case "email":
				emergencyContacts = emergencyContactsRepository.findEmergencyContactsByPersonEmail(value, pageRequest);
				break;
			case "cpf":
				emergencyContacts = emergencyContactsRepository.findEmergencyContactsByPersonCpf(value, pageRequest);
				break;
			case "id":
				emergencyContacts = emergencyContactsRepository.findEmergencyContactsByPersonId(Integer.parseInt(value), pageRequest);
				break;
			default:
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		Map<String, Object> response = new HashMap<>();
		response.put("contacts", emergencyContacts);
		response.put("currentPage", emergencyContacts.getNumber());
		response.put("totalItems", emergencyContacts.getTotalElements());
		response.put("totalPages", emergencyContacts.getTotalPages());

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// DELETE emergencycontacts?id=1
	@DeleteMapping
	public @ResponseStatus ResponseEntity<Object> deleteEmergencyContactById(@RequestParam int id) throws RestClientResponseException {
		if(!emergencyContactsRepository.existsById(id)) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		emergencyContactsRepository.deleteById(id);

		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}
}
