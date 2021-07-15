package com.dooc.health.controllers;

import com.dooc.health.infrastructure.MedicinesRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/medicines")
public class MedicinesController {
	private final MedicinesRepository medicinesRepository;

	public MedicinesController(MedicinesRepository medicinesRepository) {
		this.medicinesRepository = medicinesRepository;
	}
}
