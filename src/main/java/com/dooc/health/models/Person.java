package com.dooc.health.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "Person", schema = "DOOC")
public class Person {
	@Id
	@Column(name = "id")
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

	@OneToMany(targetEntity = Prescriptions.class, mappedBy = "person")
	@JsonBackReference("person")
	private Set<Prescriptions> prescriptions;

	@OneToMany(targetEntity = MedicalConsultation.class, mappedBy = "person")
	private Set<MedicalConsultation> medicalConsultations;

	@OneToMany(targetEntity = Address.class, mappedBy = "person")
	private Set<Address> addresses;

	@OneToMany(targetEntity = EmergencyContacts.class, mappedBy = "person")
	private Set<EmergencyContacts> emergencyContacts;

	@OneToOne(mappedBy = "person", cascade = CascadeType.ALL)
	@PrimaryKeyJoinColumn
	private MedicalInformations medicalInformations;

	public Person() { }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getBirthCity() {
		return birthCity;
	}

	public void setBirthCity(String birthCity) {
		this.birthCity = birthCity;
	}

	public Set<Prescriptions> getPrescriptions() {
		return prescriptions;
	}

	public void setPrescriptions(Set<Prescriptions> prescriptions) {
		this.prescriptions = prescriptions;
	}

	public Set<MedicalConsultation> getMedicalConsultations() {
		return medicalConsultations;
	}

	public void setMedicalConsultations(Set<MedicalConsultation> medicalConsultations) {
		this.medicalConsultations = medicalConsultations;
	}

	public Set<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(Set<Address> addresses) {
		this.addresses = addresses;
	}

	public Set<EmergencyContacts> getEmergencyContacts() {
		return emergencyContacts;
	}

	public void setEmergencyContacts(Set<EmergencyContacts> emergencyContacts) {
		this.emergencyContacts = emergencyContacts;
	}

	public MedicalInformations getMedicalInformations() {
		return medicalInformations;
	}

	public void setMedicalInformations(MedicalInformations medicalInformations) {
		this.medicalInformations = medicalInformations;
	}
}
