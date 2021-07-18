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
	public void setID(int id){
		this.ID = id;
	}

	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {this.street = street;
	}
	public String getComplement() {
		return complement;
	}
	public void setComplement(String complement) {this.complement = complement;
	}

	public String getCity() {
		return city;
	}
	public void setCity(String city) {this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {this.state = state;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {this.zipCode = zipCode;
	}
	public int getIdPerson() {
		return idPerson;
	}
	public void setIdPerson(int idPerson) {this.idPerson = idPerson;
	}












}
