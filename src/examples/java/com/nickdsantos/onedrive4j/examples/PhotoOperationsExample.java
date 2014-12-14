/**
 * Copyright (c) 2014 All Rights Reserved, nickdsantos.com
 */

package com.nickdsantos.onedrive4j.examples;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.Scanner;

import com.nickdsantos.onedrive4j.AccessToken;
import com.nickdsantos.onedrive4j.Album;
import com.nickdsantos.onedrive4j.AlbumService;
import com.nickdsantos.onedrive4j.OneDrive;
import com.nickdsantos.onedrive4j.Photo;
import com.nickdsantos.onedrive4j.PhotoService;
import com.nickdsantos.onedrive4j.Scope;

/**
 * @author Nick DS (me@nickdsantos.com)
 *
 */
public class PhotoOperationsExample extends BaseSample  {
	public static void main(String[] args) throws IOException {
		Properties prop = loadApiSettings();
		OneDrive oneDrive = new OneDrive(prop.getProperty("client_id"), prop.getProperty("client_secret"), prop.getProperty("callback_url"));AlbumService albumService = oneDrive.getAlbumService();
		PhotoService photoService = oneDrive.getPhotoService();
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
			// get first album and we'll use it to add photos to
			Album destinationAlbum = albums[0];
						
			System.out.println("==============");
			System.out.print("CHOOSE:: Specify image file to upload");
			System.out.print(">>");
			//String path = scanner.nextLine();
			String path = "/Users/someuser/Pictures/IMG_1234.jpg";
			File imgPath = new File(path);
		    byte[] bytes = Files.readAllBytes(Paths.get(path));
			
		    System.out.println("==============");
			System.out.print("UPLOAD:: Photo" + imgPath);
			int extIdx = imgPath.getName().lastIndexOf('.') + 1;
			String format = imgPath.getName().substring(extIdx);			
			Photo newPhoto = photoService.uploadPhoto(token.getAccessToken(), destinationAlbum.getId(), format, bytes);
			System.out.println("Photo Uploaded " + newPhoto.toString());
			
			System.out.println("==============");
			System.out.print("READ:: Single Photo in " + newPhoto.getId());
			Photo photo = photoService.getPhoto(token.getAccessToken(), newPhoto.getId());			
			System.out.println(photo.toString());	
			
			System.out.println("==============");
			System.out.print("UPDATE:: Photo" + newPhoto.toString());									
			Photo updatedPhoto = photoService.updatePhotoDescription(token.getAccessToken(), newPhoto.getId(), "Test-Desc-Upd");
			System.out.println("Photo Uploaded " + updatedPhoto.toString());
			
			System.out.println("==============");
			System.out.print("READ:: All Photos in " + destinationAlbum.getName());
			Photo[] photos = photoService.getPhotos(token.getAccessToken(), destinationAlbum.getId());
			for (Photo p : photos) {
				System.out.println(p.toString());
			}								
			
			System.out.println("==============");
			System.out.print("DELETE:: Photo " + photos[0].getId());
			photoService.deletePhoto(token.getAccessToken(), photos[0].getId());
		}  catch (IOException e) {
			e.printStackTrace();
		}
	}
}
