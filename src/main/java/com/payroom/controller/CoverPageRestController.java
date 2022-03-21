package com.payroom.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.payroom.model.Coverpage;
import com.payroom.service.CoverpageService;

@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST })
@RestController
@RequestMapping("/api/v1.0")

public class CoverpageRestController {

	@Autowired
	private CoverpageService coverpagesService;

	@GetMapping("/coverpages")
	public ResponseEntity<List<Coverpage>> getCoverpagesList(@RequestHeader("Authorization") String language) {
		return new ResponseEntity<>(coverpagesService.getCoverpagesList(), HttpStatus.OK);
	}

	@GetMapping("/coverpages/{coverpageId}")
	public Coverpage getCoverpageById(@PathVariable int coverpageId) {
		Coverpage coverpage = coverpagesService.getCoverpageById(coverpageId);

		if (coverpage == null) {
			throw new RuntimeException("Expense id not found -" + coverpageId);
		}
		return coverpage;
	}

}