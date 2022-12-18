package com.spotify.testcases;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import org.testng.annotations.Test;
import com.spotify.api.PlayListAPI;
import com.spotify.pojo.ErrorRoot;
import com.spotify.pojo.PlayList;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class PlayListTestCase {
	

	String newReqName = "";
	String newReqDesc = "";
	boolean newReqPublic = false;
	String playListID;
	
	@Test(priority = 1)
	public void createPlayList() {
			
		PlayList playList = new PlayList();
		playList.setName("Mathan Pojo Playlist For Batch 2");
		playList.setDescription("Mathan Pojo playlist description for Batch 2");
		playList.setPublic(false);
		Response response = PlayListAPI.postCreatePlayList(playList);
		PlayList responsePlayList = response.as(PlayList.class);
		
		assertThat(response.getStatusCode(), equalTo(201));
		assertThat(responsePlayList.getName(), equalTo(playList.getName()));
		assertThat(responsePlayList.getDescription(), equalTo(playList.getDescription()));
		assertThat(responsePlayList.getPublic(), equalTo(playList.getPublic()));
		
		JsonPath jsonRes = response.jsonPath();
		playListID = jsonRes.get("id");
		System.out.println("Play List ID is : "+playListID);
		
		newReqName = playList.getName();
		newReqDesc = playList.getDescription();
		newReqPublic = playList.getPublic();
	}
	
	@Test(priority = 2)
	public void getPlayList() {
		Response response = PlayListAPI.getPlayList(playListID);
		PlayList responsePlayList = response.as(PlayList.class);
		assertThat(responsePlayList.getName(), equalTo(newReqName));
		assertThat(responsePlayList.getDescription(), equalTo(newReqDesc));
		assertThat(responsePlayList.getPublic(), equalTo(newReqPublic));
		
	}
	
	@Test(priority = 3)
	public void updatePlaylist() {
		
		PlayList playList = new PlayList();
		playList.setName("Update Mathan Pojo Playlist For Batch 2");
		playList.setDescription("Update Mathan Pojo playlist description for Batch 2");
		playList.setPublic(false);
		
		PlayListAPI.editPlayList(playList, playListID);
	}
	
	@Test(priority = 4)
	public void notTocreatePlayList() {
				
		PlayList playList = new PlayList();
		playList.setName("");
		playList.setDescription("Mathan Pojo playlist description for Batch 2");
		playList.setPublic(false);
		Response response = PlayListAPI.postCreatePlayList(playList);
		ErrorRoot responseError = response.as(ErrorRoot.class);
		assertThat(responseError.getError().getStatus(), equalTo(400));
		assertThat(responseError.getError().getMessage(), equalTo("Missing required field: name"));
		
	}


}
