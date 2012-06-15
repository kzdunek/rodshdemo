package com.example.shdemo.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@NamedQueries({ 
	@NamedQuery(name = "angler.all", query = "Select a from Angler a"),
	@NamedQuery(name = "angler.byPhone", query = "Select a from Angler a where a.phone = :phone"),
	@NamedQuery(name = "angler.delete.all", query = "Delete From Angler"),
	@NamedQuery(name = "angler.deleteByID", query = "Delete From Angler a where a.id = :id")
})
public class Angler {

	private Long id;
	private String name = "unknown";
	private String phone = "unknown";
	private Date registrationDate = new Date();

	private List<FishingRod> fishingRods = new ArrayList<FishingRod>();

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	@Column(unique = true, nullable = false)
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Temporal(TemporalType.DATE)
	public Date getRegistrationDate() {
		return registrationDate;
	}
	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}

	// Be careful here, both with lazy and eager fetch type
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	public List<FishingRod> getFishingRods() {
		return fishingRods;
	}
	public void setFishingRods(List<FishingRod> fishingRods) {
		this.fishingRods = fishingRods;
	}
}
