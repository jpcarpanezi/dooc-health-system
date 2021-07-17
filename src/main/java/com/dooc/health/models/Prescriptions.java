package com.dooc.health.models;

import javax.persistence.*;

@Entity
@Table(name = "Prescriptions", schema = "DOOC")
public class Prescriptions {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
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

	@ManyToOne
	@JoinColumn(name = "idMedicine", insertable = false, updatable = false, nullable = false)
	private Medicines medicines;

	@ManyToOne
	@JoinColumn(name = "idConsultation", insertable = false, updatable = false, nullable = false)
	private MedicalConsultation medicalConsultation;

	@ManyToOne
	@JoinColumn(name = "idPerson", insertable=false, updatable = false, nullable = false)
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
