package com.payroom.security;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

import org.springframework.stereotype.Component;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.payroom.modelDtos.GoogleUserDto;

@Component
public class GoogleWebService {

	public GoogleUserDto getGoogleUser(String jsonToken) {
		HttpTransport transport = new NetHttpTransport();
		JsonFactory jsonFactory = new GsonFactory();
		GoogleUserDto user = null;

		try {
			GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
					.setAudience(Collections
							.singletonList("808787974559-kr5739r4u7nljergi00o51r7kamu0504.apps.googleusercontent.com"))
					.build();

			GoogleIdToken idToken = null;
			idToken = verifier.verify(jsonToken);
			if (idToken != null) {
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

			} else {
				System.out.println("Invalid ID token.");
			}
		} catch (GeneralSecurityException | IOException e) {
			e.printStackTrace();
		}

		return user;

	}

}
