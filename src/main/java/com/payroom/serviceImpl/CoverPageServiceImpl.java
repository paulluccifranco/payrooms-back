package com.payroom.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.payroom.dao.CoverpageDAO;
import com.payroom.model.Coverpage;
import com.payroom.service.CoverpageService;

@Service
@Transactional
public class CoverpageServiceImpl implements CoverpageService {

	@Autowired
	private CoverpageDAO coverpagesDAO;

	@Override
	public List<Coverpage> getCoverpagesList() {
		List<Coverpage> listCoverpages = coverpagesDAO.getCoverpagesList();
		return listCoverpages;
	}

	@Override
	public Coverpage getCoverpageById(int id) {
		Coverpage coverpage = coverpagesDAO.getCoverpageById(id);
		return coverpage;
	}

	@Override
	public void saveCoverpage(Coverpage coverpage) {
		coverpagesDAO.saveCoverpage(coverpage);

	}

	@Override
	public void deleteCoverpageById(int id) {
		coverpagesDAO.deleteCoverpage(id);
	}

}
