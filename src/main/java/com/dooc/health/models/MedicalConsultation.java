package com.dooc.health.models;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "MedicalConsultation", schema = "DOOC")
public class MedicalConsultation {
	@Id
	@Column(name = "idConsultation")
	private int ID;

	@Column(name = "consultationDate", nullable = false)
	private Timestamp consultationDate;

	@Column(name = "reason", nullable = false)
	private String reason;

	@Column(name = "diagnose", nullable = false)
	private String diagnose;

	@Column(name = "observations", nullable = false)
	private String observations;

	@Column(name = "idPerson", nullable = false)
	private int idPerson;

	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "idPerson", insertable = false, updatable = false)
	private Person person;

	public MedicalConsultation() { }

	public MedicalConsultation(int ID, Timestamp consultationDate, String reason, String diagnose, String observations, int idPerson, Person person) {
		this.ID = ID;
		this.consultationDate = consultationDate;
		this.reason = reason;
		this.diagnose = diagnose;
		this.observations = observations;
		this.idPerson = idPerson;
		this.person = person;
	}

	public int getID() {
		return ID;
	}

	public void setID(int ID) {
		this.ID = ID;
	}

	public Timestamp getConsultationDate() {
		return consultationDate;
	}

	public void setConsultationDate(Timestamp consultationDate) {
		this.consultationDate = consultationDate;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getDiagnose() {
		return diagnose;
	}

	public void setDiagnose(String diagnose) {
		this.diagnose = diagnose;
	}

	public String getObservations() {
		return observations;
	}

	public void setObservations(String observations) {
		this.observations = observations;
	}

	public int getIdPerson() {
		return idPerson;
	}

	public void setIdPerson(int idPerson) {
		this.idPerson = idPerson;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}
}

