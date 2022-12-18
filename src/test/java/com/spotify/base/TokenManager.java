package com.spotify.base;

import java.time.Instant;

import com.wrapper.api.RestAssureAPIWrapper;

import io.restassured.response.Response;

public class TokenManager {
	
	private static String access_token;
	private static Instant expiry_time;
	
	public static String getToken() {
		try {
			if(access_token == null || Instant.now().isAfter(expiry_time)) {
				System.out.println("Renewing Token ...");
				Response response = generateAccessToken();
				access_token = response.path("access_token");
				int expiryDuration = response.path("expires_in");
				expiry_time = Instant.now().plusSeconds(expiryDuration);
			}else {
				System.out.println("Token is good to use ...");
			}
			
		}catch(Exception e) {
			throw new RuntimeException("Issue in getting the Token");
		}
		return access_token;
		
	}
	
	private static Response generateAccessToken() {
		Response response = RestAssureAPIWrapper.postToken();
		
		if(response.statusCode()!= 200) {
			throw new RuntimeException("Issue in generating the token using refresh token");
		}
		
		return response;
	}

}
