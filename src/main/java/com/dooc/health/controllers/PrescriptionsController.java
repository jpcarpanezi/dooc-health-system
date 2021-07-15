package com.dooc.health.controllers;

import com.dooc.health.infrastructure.PrescriptionsRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/prescriptions")
public class PrescriptionsController {
	private final PrescriptionsRepository prescriptionsRepository;

	public PrescriptionsController(PrescriptionsRepository prescriptionsRepository) {
		this.prescriptionsRepository = prescriptionsRepository;
	}
}
