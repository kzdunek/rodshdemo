package com.example.shdemo.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({
		@NamedQuery(name = "fishingrod.all", query = "Select f From FishingRod f"),
		@NamedQuery(name = "fishingrod.delete.all", query = "Delete From FishingRod"),
		@NamedQuery(name = "fishingrod.unsold", query = "Select f from FishingRod f where f.sold = false")
})
public class FishingRod {

	private Long id;
	private String model;
	private String kind;
	private Boolean sold = false;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public Boolean getSold() {
		return sold;
	}

	public void setSold(Boolean sold) {
		this.sold = sold;
	}
}
