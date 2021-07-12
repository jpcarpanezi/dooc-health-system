package com.dooc.health.models;

import javax.persistence.*;

@Entity
@Table(name = "MedicalInformations", schema = "DOOC")
public class MedicalInformations {
	@Id
	@Column(name = "idPerson", nullable = false)
	private int id;

	@Column(name =  "bloodType", nullable = false)
	private String bloodType;

	@Column(name = "medicalConditions", nullable = true)
	private String medicalConditions;

	@Column(name = "allergies", nullable = true)
	private String allergies;

	@Column(name = "observations", nullable = true)
	private String observations;

	@OneToOne
	@MapsId
	@JoinColumn(name = "idperson")
	private Person person;

	public MedicalInformations() { }

	public MedicalInformations(int id, String bloodType, String medicalConditions, String allergies, String observations, Person person) {
		this.id = id;
		this.bloodType = bloodType;
		this.medicalConditions = medicalConditions;
		this.allergies = allergies;
		this.observations = observations;
		this.person = person;
	}

	public int getId() {
		return id;
	}

	public String getBloodType() {
		return bloodType;
	}

	public String getMedicalConditions() {
		return medicalConditions;
	}

	public String getAllergies() {
		return allergies;
	}

	public String getObservations() {
		return observations;
	}

	public Person getPerson() {
		return person;
	}
}
