package com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.model.Avatar;
import com.service.AvatarService;

@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST })
@RestController
@RequestMapping("/api/v1.0")

public class AvatarRestController {

	@Autowired
	private AvatarService avatarService;

	@GetMapping("/avatars")
	public List<Avatar> getAvatarsList() {

		return avatarService.findAvatarsList();
	}

	@GetMapping("/avatars/{avatarId}")
	public Avatar getAvatarById(@PathVariable int avatarId) {
		Avatar avatar = avatarService.findAvatarById(avatarId);

		if (avatar == null) {
			throw new RuntimeException("Avatar id not found -" + avatar);
		}
		return avatar;
	}

}