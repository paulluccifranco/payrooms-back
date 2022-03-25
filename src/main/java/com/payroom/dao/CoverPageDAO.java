package com.payroom.dao;

import java.util.List;

import com.payroom.model.Coverpage;

public interface CoverpageDAO {

	public List<Coverpage> getCoverpagesList();

	public Coverpage getCoverpageById(int id);

	public int saveCoverpage(Coverpage coverpage);

	public void deleteCoverpage(int id);

}
