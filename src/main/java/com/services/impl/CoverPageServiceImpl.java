package com.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.CoverPageDAO;
import com.model.CoverPage;
import com.service.CoverPageService;

@Service
@Transactional
public class CoverPageServiceImpl implements CoverPageService {

	@Autowired
	private CoverPageDAO coverPagesDAO;

	@Override
	public List<CoverPage> findCoverPagesList() {
		List<CoverPage> listCoverPages = coverPagesDAO.findCoverPagesList();
		return listCoverPages;
	}

	@Override
	public CoverPage findCoverPageById(int id) {
		CoverPage coverPage = coverPagesDAO.findCoverPageById(id);
		return coverPage;
	}

	@Override
	public void saveCoverPage(CoverPage coverPage) {
		coverPagesDAO.saveCoverPage(coverPage);

	}

	@Override
	public void deleteCoverPageById(int id) {
		coverPagesDAO.deleteCoverPage(id);
	}

}
