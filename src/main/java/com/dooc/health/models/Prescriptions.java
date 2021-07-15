package com.dooc.health.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Prescriptions", schema = "DOOC")
public class Prescriptions {
	@Id
	@Column(name = "idPrescription", nullable = false)
	private int ID;

	@Column(name = "idPerson", nullable = false)
	private int idPerson;

	@Column(name = "idConsultation", nullable = false)
	private int idConsultation;

	@Column(name = "idMedicine", nullable = false)
	private int idMedicine;

	@Column(name = "dosage", nullable = false)
	private String dosage;

	public Prescriptions() { }

	public Prescriptions(int ID, int idPerson, int idConsultation, int idMedicine, String dosage) {
		this.ID = ID;
		this.idPerson = idPerson;
		this.idConsultation = idConsultation;
		this.idMedicine = idMedicine;
		this.dosage = dosage;
	}

	public int getID() {
		return ID;
	}

	public int getIdPerson() {
		return idPerson;
	}

	public int getIdConsultation() {
		return idConsultation;
	}

	public int getIdMedicine() {
		return idMedicine;
	}

	public String getDosage() {
		return dosage;
	}
}
