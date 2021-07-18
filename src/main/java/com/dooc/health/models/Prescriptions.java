package com.dooc.health.models;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

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

	@OneToOne
	@JsonBackReference
	@JoinColumn(name = "idMedicine", insertable = false, updatable = false)
	private Medicines medicines;

	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "idConsultation", insertable = false, updatable = false)
	private MedicalConsultation medicalConsultation;

	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "idPerson", insertable = false, updatable = false)
	private Person person;

	public Prescriptions() { }

	public Prescriptions(int ID, int idPerson, int idConsultation, int idMedicine, String dosage, Medicines medicines, MedicalConsultation medicalConsultation, Person person) {
		this.ID = ID;
		this.idPerson = idPerson;
		this.idConsultation = idConsultation;
		this.idMedicine = idMedicine;
		this.dosage = dosage;
		this.medicines = medicines;
		this.medicalConsultation = medicalConsultation;
		this.person = person;
	}

	public int getID() {
		return ID;
	}

	public void setID(int ID) {
		this.ID = ID;
	}

	public int getIdPerson() {
		return idPerson;
	}

	public void setIdPerson(int idPerson) {
		this.idPerson = idPerson;
	}

	public int getIdConsultation() {
		return idConsultation;
	}

	public void setIdConsultation(int idConsultation) {
		this.idConsultation = idConsultation;
	}

	public int getIdMedicine() {
		return idMedicine;
	}

	public void setIdMedicine(int idMedicine) {
		this.idMedicine = idMedicine;
	}

	public String getDosage() {
		return dosage;
	}

	public void setDosage(String dosage) {
		this.dosage = dosage;
	}

	public Medicines getMedicines() {
		return medicines;
	}

	public void setMedicines(Medicines medicines) {
		this.medicines = medicines;
	}

	public MedicalConsultation getMedicalConsultation() {
		return medicalConsultation;
	}

	public void setMedicalConsultation(MedicalConsultation medicalConsultation) {
		this.medicalConsultation = medicalConsultation;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}
}
