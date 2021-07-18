package com.dooc.health.models;

import com.fasterxml.jackson.annotation.JsonBackReference;

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

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "idPerson", referencedColumnName = "id")
	@JsonBackReference
	private Person person;

	public MedicalInformations() { }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBloodType() {
		return bloodType;
	}

	public void setBloodType(String bloodType) {
		this.bloodType = bloodType;
	}

	public String getMedicalConditions() {
		return medicalConditions;
	}

	public void setMedicalConditions(String medicalConditions) {
		this.medicalConditions = medicalConditions;
	}

	public String getAllergies() {
		return allergies;
	}

	public void setAllergies(String allergies) {
		this.allergies = allergies;
	}

	public String getObservations() {
		return observations;
	}

	public void setObservations(String observations) {
		this.observations = observations;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}
}
