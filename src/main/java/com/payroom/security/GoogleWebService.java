package com.payroom.security;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.payroom.dao.PropertyDAO;
import com.payroom.modelDtos.GoogleUserDto;

@Component
public class GoogleWebService {

	@Autowired
	PropertyDAO propertyRepo;

	public GoogleUserDto getGoogleUser(String jsonToken) {
		HttpTransport transport = new NetHttpTransport();
		JsonFactory jsonFactory = new GsonFactory();
		GoogleUserDto user = null;

		try {
			GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
					.setAudience(
							Collections.singletonList(propertyRepo.findById("GOOGLE_TOKEN_BROWSER").get().getValue()))
					.build();

			GoogleIdToken idToken = null;
			idToken = verifier.verify(jsonToken);

			if (idToken == null) {
				verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory).setAudience(
						Collections.singletonList(propertyRepo.findById("GOOGLE_TOKEN_ANDROID").get().getValue()))
						.build();
				idToken = verifier.verify(jsonToken);
			}
			Payload payload = idToken.getPayload();
			String userId = payload.getSubject();
			System.out.println("User ID: " + userId);

			String email = payload.getEmail();
			Boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());
			String name = (String) payload.get("name");
			String pictureUrl = (String) payload.get("picture");
			String locale = (String) payload.get("locale");
			String familyName = (String) payload.get("family_name");
			String givenName = (String) payload.get("given_name");

			user = new GoogleUserDto(userId, email, emailVerified, name, pictureUrl, locale, familyName, givenName);
		} catch (GeneralSecurityException | IOException e) {
			e.printStackTrace();
		}

		return user;

	}

}
