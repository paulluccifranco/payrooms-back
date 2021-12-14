package com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.model.CoverPage;
import com.service.CoverPageService;

@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST })
@RestController
@RequestMapping("/api/v1.0")

public class CoverPageRestController {

	@Autowired
	private CoverPageService coverPagesService;

	@GetMapping("/coverPages")
	public List<CoverPage> getCoverPagesList() {

		return coverPagesService.findCoverPagesList();
	}

	@GetMapping("/coverPages/{coverPageId}")
	public CoverPage getCoverPageById(@PathVariable int coverPageId) {
		CoverPage coverPage = coverPagesService.findCoverPageById(coverPageId);

		if (coverPage == null) {
			throw new RuntimeException("Expense id not found -" + coverPageId);
		}
		return coverPage;
	}

}