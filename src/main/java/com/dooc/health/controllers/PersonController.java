package com.dooc.health.controllers;

import com.dooc.health.infrastructure.PersonRepository;
import com.dooc.health.models.Person;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/person")
public class PersonController {
	private final PersonRepository personRepository;

	public PersonController(PersonRepository personRepository) {
		this.personRepository = personRepository;
	}

	@GetMapping("/{email}")
	public @ResponseBody Person GetPersonByID(@PathVariable String email) {
		return personRepository.findByEmail(email);
	}
}
