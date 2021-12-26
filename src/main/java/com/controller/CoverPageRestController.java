package com.controller;

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

import com.model.CoverPage;
import com.service.CoverPageService;

import io.jsonwebtoken.Jwts;

@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST })
@RestController
@RequestMapping("/api/v1.0")

public class CoverPageRestController {

	@Autowired
	private CoverPageService coverPagesService;

	@GetMapping("/coverPages")
	public ResponseEntity<List<CoverPage>> getCoverPagesList(@RequestHeader("Authorization") String language) {

		String token = language.replace("Token ", "");
		if (token != null) {
			// Se procesa el token y se recupera el usuario.
			String user = Jwts.parser().setSigningKey("mySecretKey".getBytes()).parseClaimsJws(token).getBody()
					.getSubject();

			System.out.println(user);

		}

		return new ResponseEntity<>(coverPagesService.findCoverPagesList(), HttpStatus.OK);
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