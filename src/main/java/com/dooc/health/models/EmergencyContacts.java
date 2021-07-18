package com.dooc.health.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "EmergencyContacts", schema = "DOOC")
public class EmergencyContacts {
	@Id
	@Column(name = "idEmergContacts")
	private int ID;

	@Column(name = "emergencyName", nullable = false)
	private String emergencyName;

	@Column(name = "phone", nullable = false)
	private String phone;

	@Column(name = "kinship", nullable = false)
	private String kinship;

	@Column(name = "idPerson", nullable = false)
	private int idPerson;

	@ManyToOne
	@JoinColumn(name = "idPerson", insertable = false, updatable = false)
	@JsonBackReference
	private Person person;

	public EmergencyContacts() { }

	public int getID() {
		return ID;
	}

	public void setID(int ID) {
		this.ID = ID;
	}

	public String getEmergencyName() {
		return emergencyName;
	}

	public void setEmergencyName(String emergencyName) {
		this.emergencyName = emergencyName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getKinship() {
		return kinship;
	}

	public void setKinship(String kinship) {
		this.kinship = kinship;
	}

	public int getIdPerson() {
		return idPerson;
	}

	public void setIdPerson(int idPerson) {
		this.idPerson = idPerson;
	}

//	public Person getPerson() {
//		return person;
//	}
//
//	public void setPerson(Person person) {
//		this.person = person;
//	}
}
