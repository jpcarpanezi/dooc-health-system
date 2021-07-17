package com.dooc.health.models;
import javax.persistence.*;


@Entity
@Table(name = "Medicines", schema = "DOOC")
public class Medicines {
	@Id
	@Column(name = "idMedicines", nullable = false)
	private int ID;

	@Column(name = "drugName", nullable = false)
	private String drugName;

	@Column(name = "activeIngredient", nullable = false)
	private String activeIngredient;

	@Column(name = "formRoute", nullable = false)
	private String formRoute;

	@Column(name = "company", nullable = false)
	private String company;

	public Medicines() { }

	public Medicines(int ID, String drugName, String activeIngredient, String formRoute, String company) {
		this.ID = ID;
		this.drugName = drugName;
		this.activeIngredient = activeIngredient;
		this.formRoute = formRoute;
		this.company = company;
	}

	public int getID() {
		return ID;
	}

	public String getDrugName() {
		return drugName;
	}

	public String getActiveIngredient() {
		return activeIngredient;
	}

	public String getFormRoute() {
		return formRoute;
	}

	public String getCompany() {
		return company;
	}
}
