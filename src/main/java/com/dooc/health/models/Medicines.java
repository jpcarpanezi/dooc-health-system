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

	public int getID() {
		return ID;
	}

	public void setID(int id){
		this.ID = id;
	}

	public String getDrugName() {
		return drugName;
	}

	public void setDrugName(String drugName) {
		this.drugName = drugName;
	}

	public String getActiveIngredient() {
		return activeIngredient;
	}

	public void setActiveIngredient(String activeIngredient) {
		this.activeIngredient = activeIngredient;
	}

	public String getFormRoute() {
		return formRoute;
	}

	public void setFormRoute(String formRoute) {
		this.formRoute = formRoute;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}
}
