package com.wrapper.api;

import static com.spotify.base.SpecBuilder.*;
import static io.restassured.RestAssured.given;

import io.restassured.response.Response;

public class RestAssureAPIWrapper {
	
	public static Response post(String path,Object requestObj) {
		return given(getRequestSpec())
				.body(requestObj)
				.when()
				.post(path)
				.then()
				.spec(getResponseSpec())
				.and()
				.extract().response();
	}
	
	public static Response get(String path) {
		return given(getRequestSpec())
				.when()
				.get(path)
				.then()
				.spec(getResponseSpec())
				.statusCode(200)
				.and()
				.extract().response();
	}
	
	public static Response update(String path,Object requestObj) {
		return given(getRequestSpec())
				.body(requestObj)
				.when()
				.put(path)
				.then()
				.spec(getResponseSpec())
				.and()
				.statusCode(200)
				.and()
				.extract().response();
	}
	
	public static Response postToken() {
		return given(getRequestSpecForToken())
				.post()
				.then()
				.spec(getResponseSpec())
				.extract().response();
	}
	
	

}
