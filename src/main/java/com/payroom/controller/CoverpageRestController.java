package com.payroom.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermission;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.payroom.model.Coverpage;
import com.payroom.modelDtos.Response;
import com.payroom.service.CoverpageService;
import com.payroom.service.UserService;

@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST })
@RestController
@RequestMapping("/api/v1.0")

public class CoverpageRestController {

	@Autowired
	private CoverpageService coverpagesService;

	@Autowired
	private UserService userService;

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

	@PostMapping("/coverpages")
	public ResponseEntity<Object> saveCoverpage(@RequestParam MultipartFile file) {
		int id = 1;
		try {
			String url = "";
			Coverpage coverpage = new Coverpage();
			coverpage.setId(0);
			coverpage.setIsStock(false);
			String extension = file.getOriginalFilename().split("\\.")[1];
			id = coverpagesService.saveCoverpage(coverpage);
			String path = "/var/www/html/coverpages/" + id + "." + extension;
			File newFile = new File(path);
			try {
				file.transferTo(newFile);
				Set<PosixFilePermission> perms = new HashSet<PosixFilePermission>();
				// add owners permission
				perms.add(PosixFilePermission.OWNER_READ);
				perms.add(PosixFilePermission.OWNER_WRITE);
				perms.add(PosixFilePermission.OWNER_EXECUTE);
				// add group permissions
				perms.add(PosixFilePermission.GROUP_READ);
				// perms.add(PosixFilePermission.GROUP_WRITE);
				// perms.add(PosixFilePermission.GROUP_EXECUTE);
				// add others permissions
				perms.add(PosixFilePermission.OTHERS_READ);
				// perms.add(PosixFilePermission.OTHERS_WRITE);
				// perms.add(PosixFilePermission.OTHERS_EXECUTE);

				Files.setPosixFilePermissions(Paths.get(path), perms);
				url = "https://expensesapi.com/coverpages/" + id + "." + extension;
			} catch (IllegalStateException | IOException e) {
				Response failResponse = new Response("" + e, "La imagen no pudo guardarse");
				return new ResponseEntity<>(failResponse, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			coverpage.setUrl(url);
			coverpagesService.saveCoverpage(coverpage);
			return new ResponseEntity<>(coverpage, HttpStatus.OK);
		} catch (Exception ex) {
			Response failResponse = new Response("" + ex, "No pudo generarse la coverpage");
			return new ResponseEntity<>(failResponse, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}

}