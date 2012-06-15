package com.example.shdemo.service;

import java.util.List;

import com.example.shdemo.domain.FishingRod;
import com.example.shdemo.domain.Angler;

public interface SellingManager {
	
	void addAngler(Angler angler);
	List<Angler> getAllAnglers();
	void deleteAngler(Angler angler);
	Angler findAnglerByPhone(String phone);
	
	Long addNewFishingRod(FishingRod fishingRod);
	List<FishingRod> getAvailableFishingRods();
	void disposeFishingRod(Angler angler, FishingRod fishingRod);
	FishingRod findFishingRodById(Long id);

	List<FishingRod> getOwnedFishingRods(Angler angler);
	void sellFishingRod(Long anglerId, Long fishingrodId);
	void disposeAngler(Angler angler);
	void clearDb();
	Angler getAnglerWithMostFishingRods();

}
