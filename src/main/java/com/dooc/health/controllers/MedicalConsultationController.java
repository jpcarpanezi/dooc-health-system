package com.dooc.health.controllers;

import com.dooc.health.infrastructure.MedicalConsultationRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/medicalconsultation")
public class MedicalConsultationController {
	private final MedicalConsultationRepository medicalConsultationRepository;

	public MedicalConsultationController(MedicalConsultationRepository medicalConsultationRepository) {
		this.medicalConsultationRepository = medicalConsultationRepository;
	}
}
