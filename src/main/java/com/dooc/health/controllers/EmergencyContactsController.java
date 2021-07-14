package com.dooc.health.controllers;

import com.dooc.health.infrastructure.EmergencyContactsRepository;
import com.dooc.health.models.EmergencyContacts;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/emergencycontacts")
public class EmergencyContactsController {
	private final EmergencyContactsRepository emergencyContactsRepository;

	public EmergencyContactsController(EmergencyContactsRepository emergencyContactsRepository) {
		this.emergencyContactsRepository = emergencyContactsRepository;
	}

	@GetMapping("/getByPersonId/{id}")
	public @ResponseBody EmergencyContacts getEmergencyContactsByID(@PathVariable int id) {
		return emergencyContactsRepository.findByPersonId(id);
	}
}
