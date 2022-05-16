package com.payroom.service;

import java.util.List;

import com.payroom.model.Coverpage;

public interface CoverpageService {

	public List<Coverpage> getCoverpagesList();

	public Coverpage getCoverpageById(int id);

	public int saveCoverpage(Coverpage coverpage);

	public void deleteCoverpageById(int id);

}
