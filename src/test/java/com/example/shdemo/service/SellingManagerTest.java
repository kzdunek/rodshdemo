package com.example.shdemo.service;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
//import org.springframework.transaction.annotation.Transactional;

import com.example.shdemo.domain.FishingRod;
import com.example.shdemo.domain.Angler;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/beans.xml")
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
//@Transactional
public class SellingManagerTest {

	@Autowired
	SellingManager sellingManager;
	private final String Kind_1 = "spinning";
	private final String Kind_2 = "bat";
	private final String MODEL_1 = "jaxon";
	private final String MODEL_2 = "dragon";
	private final String Name_1 = "Maciej";
	private final String Name_2 = "Adam";
	private final String Phone_1 = "500214141";
	private final String Phone_2 = "632145635";
	@Test
	public void addAnglerCheck() {

		List<Angler> retrievedAnglers = sellingManager.getAllAnglers();

		for (Angler Angler : retrievedAnglers) {
			if (Angler.getPhone().equals(Phone_1)) {
				sellingManager.deleteAngler(Angler);
			}
		}

		Angler angler = new Angler();
		angler.setName(Name_1);
		angler.setPhone(Phone_1);

		sellingManager.addAngler(angler);

		Angler retrievedAngler = sellingManager.findAnglerByPhone(Phone_1);

		assertEquals(Name_1, retrievedAngler.getName());
		assertEquals(Phone_1, retrievedAngler.getPhone());
	}

	@Test
	public void addFishingRodCheck() {

		FishingRod FishingRod = new FishingRod();
		FishingRod.setKind(Kind_1);
		FishingRod.setModel(MODEL_1);

		Long FishingRodId = sellingManager.addNewFishingRod(FishingRod);

		FishingRod retrievedFishingRod = sellingManager.findFishingRodById(FishingRodId);
		assertEquals(Kind_1, retrievedFishingRod.getKind());
		assertEquals(MODEL_1, retrievedFishingRod.getModel());

	}

	@Test
	public void sellFishingRodCheck() {
		sellingManager.clearDb();
		Angler person = new Angler();
		person.setName(Name_2);
		person.setPhone(Phone_2);

		sellingManager.addAngler(person);

		Angler retrievedAngler = sellingManager.findAnglerByPhone(Phone_2);

		FishingRod FishingRod = new FishingRod();
		FishingRod.setKind(Kind_2);
		FishingRod.setModel(MODEL_2);

		Long FishingRodId = sellingManager.addNewFishingRod(FishingRod);

		sellingManager.sellFishingRod(retrievedAngler.getId(), FishingRodId);

		List<FishingRod> ownedFishingRods = sellingManager.getOwnedFishingRods(retrievedAngler);

		assertEquals(1, ownedFishingRods.size());
		assertEquals(Kind_2, ownedFishingRods.get(0).getKind());
		assertEquals(MODEL_2, ownedFishingRods.get(0).getModel());
	}

	@Test
	public void disposeFishingRodCheck() {
		sellingManager.clearDb();
		Angler Angler = new Angler();
		Angler.setName(Name_1);
		Angler.setPhone(Phone_1);
		sellingManager.addAngler(Angler);
		FishingRod FishingRod = new FishingRod();
		FishingRod.setKind(Kind_1);
		FishingRod.setModel(MODEL_1);
		Long FishingRodId = sellingManager.addNewFishingRod(FishingRod);
		Angler retrievedAngler = sellingManager.findAnglerByPhone(Phone_1);
		sellingManager.sellFishingRod(retrievedAngler.getId(), FishingRodId);
		List<FishingRod> ownedFishingRods = sellingManager.getOwnedFishingRods(retrievedAngler);
		assertEquals(1, ownedFishingRods.size());
		sellingManager.disposeFishingRod(Angler, FishingRod);
		assertEquals(0, Angler.getFishingRods().size());	
	}
	@Test
	public void disposeAnglerCheck() {
		sellingManager.clearDb();
		Angler Angler = new Angler();
		Angler.setName(Name_1);
		Angler.setPhone(Phone_1);
		sellingManager.addAngler(Angler);
		FishingRod FishingRod = new FishingRod();
		FishingRod.setKind(Kind_1);
		FishingRod.setModel(MODEL_1);
		Long FishingRodId = sellingManager.addNewFishingRod(FishingRod);
		FishingRod FishingRod2 = new FishingRod();
		FishingRod2.setKind(Kind_2);
		FishingRod2.setModel(MODEL_2);
		Long FishingRodId2 = sellingManager.addNewFishingRod(FishingRod2);
		Angler retrievedAngler = sellingManager.findAnglerByPhone(Phone_1);
		sellingManager.sellFishingRod(retrievedAngler.getId(), FishingRodId);
		sellingManager.sellFishingRod(retrievedAngler.getId(), FishingRodId2);
		sellingManager.disposeAngler(Angler);
		assertEquals(0, Angler.getFishingRods().size());
		assertEquals(0, sellingManager.getAllAnglers().size());
	}
	@Test
	public void AnglerMostFishingRods() {
		sellingManager.clearDb();
		Angler Angler = new Angler();
		Angler.setName(Name_1);
		Angler.setPhone(Phone_1);
		sellingManager.addAngler(Angler);
		Angler Angler2 = new Angler();
		Angler2.setName(Name_2);
		Angler2.setPhone(Phone_2);
		sellingManager.addAngler(Angler2);
		FishingRod FishingRod = new FishingRod();
		FishingRod.setKind(Kind_1);
		FishingRod.setModel(MODEL_1);
		Long FishingRodId = sellingManager.addNewFishingRod(FishingRod);
		FishingRod FishingRod2 = new FishingRod();
		FishingRod2.setKind(Kind_2);
		FishingRod2.setModel(MODEL_2);
		Long FishingRodId2 = sellingManager.addNewFishingRod(FishingRod2);
		FishingRod FishingRod3 = new FishingRod();
		FishingRod3.setKind(Kind_1);
		FishingRod3.setModel(MODEL_2);
		Long FishingRodId3 = sellingManager.addNewFishingRod(FishingRod3);
		Angler retrievedAngler = sellingManager.findAnglerByPhone(Phone_1);
		Angler retrievedAngler2 = sellingManager.findAnglerByPhone(Phone_2);
		sellingManager.sellFishingRod(retrievedAngler.getId(), FishingRodId);
		sellingManager.sellFishingRod(retrievedAngler.getId(), FishingRodId2);
		sellingManager.sellFishingRod(retrievedAngler2.getId(), FishingRodId3);
		Angler max = sellingManager.getAnglerWithMostFishingRods();
		assertEquals(Name_1, max.getName());
		assertEquals(Phone_1, max.getPhone());
	}
}
