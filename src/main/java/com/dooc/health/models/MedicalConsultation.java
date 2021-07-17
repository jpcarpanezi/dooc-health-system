package com.dooc.health.models;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "MedicalConsultation", schema = "DOOC")
public class MedicalConsultation {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "idConsultation", nullable = false)
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
	@MapsId
	@JoinColumn(name = "idperson")
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

	public Timestamp getConsultationDate() {
		return consultationDate;
	}

	public String getReason() {
		return reason;
	}

	public String getDiagnose() {
		return diagnose;
	}

	public String getObservations() {
		return observations;
	}

	public int getIdPerson() {
		return idPerson;
	}
}

