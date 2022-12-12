package com.spotify.testcases;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.matchesPattern;
import static org.hamcrest.MatcherAssert.*;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.spotify.pojo.PlayList;

import io.restassured.authentication.AuthenticationScheme;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class PlayListTestCase {
	
	String uri = "https://api.spotify.com/v1";
	String accessToken = "BQB3hug5qpY9h6W7yJje0S44vniIGUuReTILq9fZL43oMflVKqmCIa_O08QXL-n6VXKGffjLNGZ1XO80exwArn7UdR3d4G9vw199ExXSUA_sIRIERmIm8mc07PkUpY1dcML2ET3RNMiBksg-UCEiulG5r1AViGGDt_zzkZCcvLEzmaxhNSp16L6mnSBxaM2Afr_WgdabTearZfUS9Cp35xKnhQTclLla0ImIOj7f4y1WVusSOrDp-3Z3SnrISM-Ko53-ZAmahjN1FtvI";
	String userID = "31cwetdnd4oeo5wq2c7khzkqt5oe";	
	RequestSpecification requestSpecification;
	ResponseSpecification responseSpecification;
	String newReqName = "";
	String newReqDesc = "";
	boolean newReqPublic = false;
	String playListID;
	
	@BeforeClass
	public void requestSpec() {
		
		RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
		requestSpecBuilder
		//.setAuth(oauth2(accessToken))
		.addHeader("Authorization", "Bearer "+accessToken)
		.setContentType(ContentType.JSON)
		.setBaseUri(uri)
		.log(LogDetail.ALL);
		requestSpecification = requestSpecBuilder.build();
		
		ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder();
		
		responseSpecification = responseSpecBuilder
		.log(LogDetail.ALL).build();
	}
	
	@Test(priority = 1)
	public void createPlayList() {
			
		PlayList playList = new PlayList();
		playList.setName("Mathan Pojo Playlist For Batch 2");
		playList.setDescription("Mathan Pojo playlist description for Batch 2");
		playList.setPublic(false);
		
		Response response = given(requestSpecification)
		//.auth().oauth2(accessToken)
		.body(playList)
		.when()
		.post("/users/"+userID+"/playlists")
		.then()
		.spec(responseSpecification)
		.and()
		.statusCode(201)
		.extract().response();
		
		PlayList responsePlayList = response.as(PlayList.class);
		
		assertThat(responsePlayList.getName(), equalTo(playList.getName()));
		assertThat(responsePlayList.getDescription(), equalTo(playList.getDescription()));
		assertThat(responsePlayList.getPublic(), equalTo(playList.getPublic()));
		newReqName = playList.getName();
		newReqDesc = playList.getDescription();
		newReqPublic = playList.getPublic();
	}
	
	@Test(priority = 2)
	public void getPlayList() {
		PlayList responsePlayList = given(requestSpecification)
		.when()
		.get("/playlists/"+playListID)
		.then()
		.spec(responseSpecification)
		.statusCode(200)
		.and()
		.extract().response().as(PlayList.class);
		
		assertThat(responsePlayList.getName(), equalTo(newReqName));
		assertThat(responsePlayList.getDescription(), equalTo(newReqDesc));
		assertThat(responsePlayList.getPublic(), equalTo(newReqPublic));
		
	}
	
	@Test(priority = 3)
	public void updatePlaylist() {
		String payLoad = "{\r\n"
				+ "  \"name\": \"Update Mathan Playlist For Batch 2\",\r\n"
				+ "  \"description\": \"Update Mathan playlist description for Batch 2\",\r\n"
				+ "  \"public\": false\r\n"
				+ "}";
		given(requestSpecification)
		.body(payLoad)
		.when()
		.put("/playlists/"+playListID)
		.then()
		.spec(responseSpecification)
		.and()
		.statusCode(200);
	}
	
	@Test(priority = 4)
	public void notTocreatePlayList() {
		String payLoad = "{\r\n"
				+ "  \"name\": \"\",\r\n"
				+ "  \"description\": \"Mathan playlist description for Batch 2\",\r\n"
				+ "  \"public\": false\r\n"
				+ "}";
		given(requestSpecification)
		//.auth().oauth2(accessToken)
		.body(payLoad)
		.when()
		.post("/users/"+userID+"/playlists")
		.then()
		.spec(responseSpecification)
		.and()
		.statusCode(400)
		.and()
		.body("error.status", equalTo(400),
				"error.message",equalTo("Missing required field: name"));
	}


}
