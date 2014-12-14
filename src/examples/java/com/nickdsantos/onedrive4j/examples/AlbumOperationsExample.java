/**
 * Copyright (c) 2014 All Rights Reserved, nickdsantos.com
 */

package com.nickdsantos.onedrive4j.examples;

import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;
import java.util.UUID;

import com.nickdsantos.onedrive4j.AccessToken;
import com.nickdsantos.onedrive4j.Album;
import com.nickdsantos.onedrive4j.AlbumService;
import com.nickdsantos.onedrive4j.OneDrive;
import com.nickdsantos.onedrive4j.Scope;

/**
 * @author Nick DS (me@nickdsantos.com)
 *
 */
public class AlbumOperationsExample extends BaseSample {
	public static void main(String[] args) throws IOException {
		Properties prop = loadApiSettings();
		OneDrive oneDrive = new OneDrive(prop.getProperty("client_id"), prop.getProperty("client_secret"), prop.getProperty("callback_url"));
		AlbumService albumService = oneDrive.getAlbumService();
		String authzUrl = oneDrive.authorize(new Scope[] { Scope.SKYDRIVE_UPDATE,  Scope.PHOTOS });
		
		try {
			System.out.println("Follow this URL to authorise yourself on OneDrive");
	        System.out.println(authzUrl);
	        System.out.println("Paste in the access code from the URL that you got redirected to:");
	        System.out.print(">>");	        
			Scanner scanner = new Scanner(System.in);
			String code = scanner.nextLine();
			
			AccessToken token = oneDrive.getAccessToken(code);
			System.out.println("AuthenticationToken: " + token.getAuthenticationToken());
			System.out.println("AccessToken: " + token.getAccessToken());
			
			System.out.println("==============");
			System.out.print("READ:: OneDrive Albums");
			Album[] albums = albumService.getAlbums(token.getAccessToken());
			for (Album a : albums) {
				System.out.println(a.toString());				
			}
			
			System.out.println("==============");
			System.out.print("CREATE:: OneDrive Album");
			String albumName = "Test-" + UUID.randomUUID().toString();
			String albumDesc = "Test-Desc-" + UUID.randomUUID().toString();
			
			Album album = albumService.createAlbum(token.getAccessToken(), albumName, albumDesc);
			System.out.println("Album Created " + album.toString());
			
			System.out.println("==============");
			System.out.print("READ:: OneDrive Album (" + album.getId() + ")");
			album = albumService.getAlbum(token.getAccessToken(), album.getId());
			System.out.println("Album Read " + album.toString());
			
			System.out.println("==============");
			System.out.print("UPDATE:: OneDrive Album (" + album.getId() + ")");
			albumName = "Test-Upd-" + UUID.randomUUID().toString();
			albumDesc = "Test-Upd-Desc-" + UUID.randomUUID().toString();
			
			album = albumService.updateAlbum(token.getAccessToken(), album.getId(), albumName, albumDesc);
			System.out.println("Album Updated " + album.toString());
			
			System.out.println("==============");
			System.out.print("DELETE:: OneDrive Album (" + album.getId() + ")");
			albumService.deleteAlbum(token.getAccessToken(), album.getId());
			System.out.println("Album Deleted " + album.toString());
			
			scanner.close();					
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
