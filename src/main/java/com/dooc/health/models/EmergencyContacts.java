package com.dooc.health.models;

import javax.persistence.*;

@Entity
@Table(name = "EmergencyContacts", schema = "DOOC")
public class EmergencyContacts {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "idEmergContacts", nullable = false)
	private int ID;

	@Column(name = "emergencyName", nullable = false)
	private String emergencyName;

	@Column(name = "phone", nullable = false)
	private String phone;

	@Column(name = "kinship", nullable = false)
	private String kinship;

	@Column(name = "idPerson", nullable = false)
	private int idPerson;

	@OneToOne
	@MapsId
	@JoinColumn(name = "idperson")
	private Person person;

	public EmergencyContacts(int ID, String emergencyName, String phone, String kinship, int idPerson, Person person) {
		this.ID = ID;
		this.emergencyName = emergencyName;
		this.phone = phone;
		this.kinship = kinship;
		this.idPerson = idPerson;
		this.person = person;
	}

	public EmergencyContacts() {

	}

	public int getID() {
		return ID;
	}

	public String getEmergencyName() {
		return emergencyName;
	}

	public String getPhone() {
		return phone;
	}

	public String getKinship() {
		return kinship;
	}

	public int getIdPerson() {
		return idPerson;
	}

	public Person getPerson() {
		return person;
	}

	@ManyToOne
	private Person manyToOne;

	public Person getManyToOne() {
		return manyToOne;
	}

	public void setManyToOne(Person manyToOne) {
		this.manyToOne = manyToOne;
	}
}
