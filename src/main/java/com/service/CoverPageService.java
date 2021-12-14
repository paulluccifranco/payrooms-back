package com.service;

import java.util.List;

import com.model.CoverPage;

public interface CoverPageService {

	public List<CoverPage> findCoverPagesList();

	public CoverPage findCoverPageById(int id);

	public void saveCoverPage(CoverPage coverPage);

	public void deleteCoverPageById(int id);

}
