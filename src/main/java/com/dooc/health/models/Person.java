package com.dooc.health.models;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Person", schema = "DOOC")
public class Person {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false)
	private int id;

	@Column(name = "personName", nullable = false)
	private String personName;

	@Column(name = "cpf", nullable = false)
	private String cpf;

	@Column(name = "phone", nullable = false)
	private String phone;

	@Column(name = "birthDate", nullable = false)
	private Date birthDate;

	@Column(name = "email", nullable = false)
	private String email;

	@Column(name = "birthCity", nullable = false)
	private String birthCity;

	public Person(int id, String personName, String cpf, String phone, Date birthDate, String email, String birthCity) {
		this.id = id;
		this.personName = personName;
		this.cpf = cpf;
		this.phone = phone;
		this.birthDate = birthDate;
		this.email = email;
		this.birthCity = birthCity;
	}

	public Person() {

	}

	public int getId() {
		return id;
	}

	public String getPersonName() {
		return personName;
	}

	public String getCpf() {
		return cpf;
	}

	public String getPhone() {
		return phone;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public String getEmail() {
		return email;
	}

	public String getBirthCity() {
		return birthCity;
	}
}
