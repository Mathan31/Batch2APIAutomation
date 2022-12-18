package com.spotify.base;
import java.util.HashMap;
import java.util.Map;

import com.utilities.PropertiesReader;
import static com.spotify.base.TokenManager.*;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;


public class SpecBuilder {
	public static String fileName = "Config";
	public static String uri = PropertiesReader.getPropertyValue(fileName, "uri");
	public static String accessToken = "";
	public static String tokenuri = PropertiesReader.getPropertyValue(fileName, "token_uri");
	public static String client_id = PropertiesReader.getPropertyValue(fileName, "client_id");
	public static String client_secret = PropertiesReader.getPropertyValue(fileName, "client_secret");
	public static String refresh_token = PropertiesReader.getPropertyValue(fileName, "refresh_token");
	
	public static RequestSpecification getRequestSpec() {
		accessToken = getToken();
		return new RequestSpecBuilder().addHeader("Authorization", "Bearer "+accessToken)
				.setContentType(ContentType.JSON)
				.setBaseUri(uri)
				.log(LogDetail.ALL).build();
	}
	
	public static ResponseSpecification getResponseSpec() {
		return new ResponseSpecBuilder().log(LogDetail.ALL).build();
	}
	
	public static RequestSpecification getRequestSpecForToken() {
		Map<String, String> formParam = new HashMap<>();
		formParam.put("client_id", client_id);
		formParam.put("client_secret", client_secret); 
		formParam.put("refresh_token", refresh_token);
		formParam.put("grant_type", "refresh_token");
		return new RequestSpecBuilder().setContentType(ContentType.URLENC).addFormParams(formParam)
				.setBaseUri(tokenuri).log(LogDetail.ALL).build();
	}
	
}
