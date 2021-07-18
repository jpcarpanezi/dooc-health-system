package com.dooc.health.controllers;

import com.dooc.health.infrastructure.PersonRepository;
import com.dooc.health.models.Person;
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
@RequestMapping("/person")
public class PersonController {
	private final PersonRepository personRepository;

	public PersonController(PersonRepository personRepository) {
		this.personRepository = personRepository;
	}

	// POST /person
	@PostMapping
	public @ResponseStatus ResponseEntity<Person> insertPerson(@RequestBody Person person) throws RestClientResponseException {
		// TODO: Descobrir como inserir endereços junto ao cadastro de uma pessoa
		Person insertPerson = new Person();
		insertPerson.setPersonName(person.getPersonName());
		insertPerson.setEmail(person.getEmail());
		insertPerson.setCpf(person.getCpf());
		insertPerson.setPhone(person.getPhone());
		insertPerson.setBirthDate(person.getBirthDate());
		insertPerson.setEmail(person.getEmail());
		insertPerson.setBirthCity(person.getBirthCity());

		personRepository.save(insertPerson);
		return new ResponseEntity<Person>(person, HttpStatus.CREATED);
	}

	// GET person?page=1&limit=10
	@GetMapping
	public @ResponseStatus ResponseEntity<Map<String, Object>> getAllPeople(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int limit) {
		// TODO: Descobrir como fazer pra paginação começar em 1 e não em 0
		if (limit > 50) limit = 50;

		Page<Person> people = personRepository.findAll(PageRequest.of(page, limit, Sort.by("personName").ascending()));
		people.getTotalPages();

		Map<String, Object> response = new HashMap<>();
		response.put("people", people);
		response.put("currentPage", people.getNumber());
		response.put("totalItems", people.getTotalElements());
		response.put("totalPages", people.getTotalPages());

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// GET person/item?selectBy=email&email=email@example.com
	@GetMapping("/item")
	public @ResponseStatus ResponseEntity<Person> getPersonByParam(@RequestParam(defaultValue = "email") String selectBy, @RequestParam String value) throws RestClientResponseException {
		Person person;
		switch (selectBy) {
			case "email":
				person = personRepository.getPersonByEmail(value);
				break;
			case "cpf":
				person = personRepository.getPersonByCpf(value);
				break;
			case "id":
				person = personRepository.getPersonById(Integer.parseInt(value));
				break;
			default:
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		if (person == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<Person>(person, HttpStatus.OK);
	}

	// PUT person?updateMethod=email&value=email@example.com
	@PutMapping
	public @ResponseStatus ResponseEntity<Person> updatePerson(@RequestBody Person person, @RequestParam(defaultValue = "email") String updateMethod, @RequestParam String value) throws RestClientResponseException {
		Person updatePerson;
		switch (updateMethod) {
			case "email":
				updatePerson = personRepository.getPersonByEmail(value);
				break;
			case "cpf":
				updatePerson = personRepository.getPersonByCpf(value);
				break;
			case "id":
				updatePerson = personRepository.getPersonById(Integer.parseInt(value));
				break;
			default:
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		if (updatePerson == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		updatePerson.setPersonName(person.getPersonName());
		updatePerson.setEmail(person.getEmail());
		updatePerson.setCpf(person.getCpf());
		updatePerson.setPhone(person.getPhone());
		updatePerson.setBirthDate(person.getBirthDate());
		updatePerson.setEmail(person.getEmail());
		updatePerson.setBirthCity(person.getBirthCity());

		personRepository.save(updatePerson);

		return new ResponseEntity<Person>(updatePerson, HttpStatus.OK);
	}


	// DELETE person?deleteMethod=email&value=email@example.com
	@DeleteMapping
	public @ResponseStatus ResponseEntity<HttpStatus> deletePerson(@RequestParam(defaultValue = "email") String deleteMethod, @RequestParam String value) throws RestClientResponseException {
		// TODO: Descobrir por que o método deletePersonById() ou deletePersonByEmail() não estão funcionando.
		Person person;
		switch (deleteMethod) {
			case "email":
				person = personRepository.getPersonByEmail(value);
				break;
			case "cpf":
				person = personRepository.getPersonByCpf(value);
				break;
			case "id":
				person = personRepository.getPersonById(Integer.parseInt(value));
				break;
			default:
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		if (person == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		personRepository.deleteById(person.getId());

		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}
}
