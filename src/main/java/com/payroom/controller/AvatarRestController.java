package com.payroom.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.payroom.model.Avatar;
import com.payroom.service.AvatarService;

@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST })
@RestController
@RequestMapping("/api/v1.0")

public class AvatarRestController {

	@Autowired
	private AvatarService avatarService;

	@GetMapping("/avatars")
	public List<Avatar> getAvatarsList() {

		return avatarService.getAvatarsList();
	}

	@GetMapping("/avatars/{avatarId}")
	public Avatar getAvatarById(@PathVariable int avatarId) {
		Avatar avatar = avatarService.getAvatarById(avatarId);

		if (avatar == null) {
			throw new RuntimeException("Avatar id not found -" + avatar);
		}
		return avatar;
	}

	@PostMapping("/avatars")
	public String saveAvatar(@RequestParam MultipartFile file) {
		File newFile = new File("D:\\\\1.jpg");
		try {
			file.transferTo(newFile);
		} catch (IllegalStateException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "";
	}

}