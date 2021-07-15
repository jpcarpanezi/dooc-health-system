package com.dooc.health.controllers;

import com.dooc.health.infrastructure.AddressRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/address")
public class AddressController {
	private final AddressRepository addressRepository;

	public AddressController(AddressRepository addressRepository) {
		this.addressRepository = addressRepository;
	}
}
