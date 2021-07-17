package com.dooc.health.models;

import javax.persistence.*;

@Entity
@Table(name = "Address", schema = "DOOC")
public class Address {
	@Id
	@Column(name = "idAddress", nullable = false)
	private int ID;

	@Column(name = "street", nullable = false)
	private String street;

	@Column(name = "complement", nullable = true)
	private String complement;

	@Column(name = "city", nullable = false)
	private String city;

	@Column(name = "state", nullable = false)
	private String state;

	@Column(name = "zipCode", nullable = false)
	private String zipCode;

	@Column(name = "idPerson", nullable = false)
	private int idPerson;

	@ManyToOne
	@JoinColumn(name = "idPerson", insertable=false, updatable = false, nullable = false)
	private Person person;

	public Address(int ID, String street, String complement, String city, String state, String zipCode, int idPerson, Person person) {
		this.ID = ID;
		this.street = street;
		this.complement = complement;
		this.city = city;
		this.state = state;
		this.zipCode = zipCode;
		this.idPerson = idPerson;
		this.person = person;
	}

	public Address() { };

	public int getID() {
		return ID;
	}

	public String getStreet() {
		return street;
	}

	public String getComplement() {
		return complement;
	}

	public String getCity() {
		return city;
	}

	public String getState() {
		return state;
	}

	public String getZipCode() {
		return zipCode;
	}

	public int getIdPerson() {
		return idPerson;
	}
}