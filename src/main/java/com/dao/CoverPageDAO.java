package com.dao;

import java.util.List;

import com.model.CoverPage;

public interface CoverPageDAO {

	public List<CoverPage> findCoverPagesList();

	public CoverPage findCoverPageById(int id);

	public void saveCoverPage(CoverPage coverPage);

	public void deleteCoverPage(int id);

}
