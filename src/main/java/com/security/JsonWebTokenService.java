package com.security;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JsonWebTokenService {

	private final String HEADER = "Authorization";
	private final String PREFIX = "Token ";
	private final String SECRET = "mySecretKey";

	public String generateJWTToken(String userId) {
		String secretKey = "mySecretKey";
		List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER");

		String token = Jwts.builder().setId("expensesJWT").setSubject(userId)
				.claim("authorities",
						grantedAuthorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7))
				.signWith(SignatureAlgorithm.HS512, secretKey.getBytes()).compact();

		return "Token " + token;
	}

	public int validateUserJWT(HttpServletRequest request) {
		int userId = 0;
		String token = request.getHeader(HEADER);
		if (token != null) {
			// Se procesa el token y se recupera el usuario.
			String user = Jwts.parser().setSigningKey(SECRET.getBytes()).parseClaimsJws(token.replace(PREFIX, ""))
					.getBody().getSubject();

			if (user != null) {
				userId = Integer.parseInt(user);
				return userId;
			}
			return userId;
		}
		return userId;
	}

}
