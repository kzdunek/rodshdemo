package com.example.shdemo.service;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.shdemo.domain.FishingRod;
import com.example.shdemo.domain.Angler;

@Component
@Transactional
public class SellingMangerHibernateImpl implements SellingManager {

	@Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@Override
	public void addAngler(Angler angler) {
		angler.setId(null);
		sessionFactory.getCurrentSession().persist(angler);
	}
	
	@Override
	public void deleteAngler(Angler angler) {
		angler = (Angler) sessionFactory.getCurrentSession().get(Angler.class,
				angler.getId());
		
		for (FishingRod fishingRod : angler.getFishingRods()) {
			fishingRod.setSold(false);
			sessionFactory.getCurrentSession().update(fishingRod);
		}
		sessionFactory.getCurrentSession().delete(angler);
	}

	@Override
	public List<FishingRod> getOwnedFishingRods(Angler angler) {
		angler = (Angler) sessionFactory.getCurrentSession().get(Angler.class,
				angler.getId());
		List<FishingRod> fishingRods = new ArrayList<FishingRod>(angler.getFishingRods());
		return fishingRods;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Angler> getAllAnglers() {
		return sessionFactory.getCurrentSession().getNamedQuery("angler.all")
				.list();
	}

	@Override
	public Angler findAnglerByPhone(String phone) {
		return (Angler) sessionFactory.getCurrentSession().getNamedQuery("angler.byPhone").setString("phone", phone).uniqueResult();
	}


	@Override
	public Long addNewFishingRod(FishingRod fishingRod) {
		fishingRod.setId(null);
		return (Long) sessionFactory.getCurrentSession().save(fishingRod);
	}

	@Override
	public void sellFishingRod(Long anglerId, Long fishingRodId) {
		Angler angler = (Angler) sessionFactory.getCurrentSession().get(
				Angler.class, anglerId);
		FishingRod fishingRod = (FishingRod) sessionFactory.getCurrentSession()
				.get(FishingRod.class, fishingRodId);
		fishingRod.setSold(true);
		angler.getFishingRods().add(fishingRod);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<FishingRod> getAvailableFishingRods() {
		return sessionFactory.getCurrentSession().getNamedQuery("fishingrod.unsold")
				.list();
	}
	@Override
	public void disposeFishingRod(Angler angler, FishingRod fishingRod) {

		angler = (Angler) sessionFactory.getCurrentSession().get(Angler.class,
				angler.getId());
		fishingRod = (FishingRod) sessionFactory.getCurrentSession().get(FishingRod.class,
				fishingRod.getId());

		FishingRod toRemove = null;
		
		for (FishingRod fFishingRod : angler.getFishingRods())
			if (fFishingRod.getId().equals(fishingRod.getId())) {
				toRemove = fFishingRod;
				break;
			}

		if (toRemove != null)
			angler.getFishingRods().remove(toRemove);

		fishingRod.setSold(false);
	}

	@Override
	public FishingRod findFishingRodById(Long id) {
		return (FishingRod) sessionFactory.getCurrentSession().get(FishingRod.class, id);
	}
	
	@Override
	public void disposeAngler(Angler angler) {
		angler = (Angler) sessionFactory.getCurrentSession().get(Angler.class,
				angler.getId());
		angler.getFishingRods().clear();
		sessionFactory.getCurrentSession().delete(angler);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void clearDb() {
		List<Angler> anglers = sessionFactory.getCurrentSession().getNamedQuery("angler.all").list();
		for(Angler aAngler : anglers) {
			Angler angler = (Angler) sessionFactory.getCurrentSession().get(Angler.class,
					aAngler.getId());
			for (FishingRod fishingRod : angler.getFishingRods()) {
				fishingRod.setSold(false);
				sessionFactory.getCurrentSession().update(fishingRod);
			}
			sessionFactory.getCurrentSession().delete(angler);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Angler getAnglerWithMostFishingRods() {
		int count = 0;
		Angler maxAngler = null;
		List<Angler> anglers = sessionFactory.getCurrentSession().getNamedQuery("angler.all").list();
		for(Angler aAngler : anglers) {
			int i=0;
			for (FishingRod fishingRod : aAngler.getFishingRods()) {
				i++;
			}
			if(i > count) {
				count = i;
				maxAngler = aAngler;
			}
		}		
		return maxAngler;
	}
}
