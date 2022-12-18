package com.spotify.api;

import com.spotify.pojo.PlayList;
import com.utilities.PropertiesReader;
import com.wrapper.api.RestAssureAPIWrapper;

import io.restassured.response.Response;

public class PlayListAPI {
	public static String userID = PropertiesReader.getPropertyValue("Config", "user_id");
	
	public static Response postCreatePlayList(PlayList playList) {
		return RestAssureAPIWrapper.post("/users/"+userID+"/playlists", playList);
	}
	
	public static Response getPlayList(String playListID) {
		return RestAssureAPIWrapper.get("/playlists/"+playListID);
	}
	
	public static Response editPlayList(PlayList playList,String playListID) {
		return RestAssureAPIWrapper.update("/playlists/"+playListID, playList);
	}
	
}
