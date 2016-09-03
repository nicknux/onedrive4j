package com.nickdsantos.onedrive4j.examples;

import com.nickdsantos.onedrive4j.*;

import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

/**
 * A OneDrive Drive API example
 */
public class DriveOperationsExample extends BaseSample {
	public static void main(String[] args) throws IOException {
		Properties prop = loadApiSettings();
		OneDrive oneDrive = new OneDrive(prop.getProperty("client_id"), prop.getProperty("client_secret"), prop.getProperty("callback_url"));
		DriveService driveService = oneDrive.getDriveService();
		String authzUrl = oneDrive.authorize(new Scope[] { Scope.SKYDRIVE_UPDATE,  Scope.SKYDRIVE });
		
		try {
			System.out.println("Follow this URL to authorise yourself on OneDrive");
	        System.out.println(authzUrl);
	        System.out.println("Paste in the access code from the URL that you got redirected to:");
	        System.out.print(">>");	        
			Scanner scanner = new Scanner(System.in);
			String code = scanner.nextLine();
			
			AccessToken token = oneDrive.getAccessToken(code);
			System.out.println("UserId: " + token.getUserId());
			System.out.println("AccessToken: " + token.getAccessToken());
			
			System.out.println("==============");
			System.out.print("READ:: OneDrive Drives");
			List<Drive> drives = driveService.getDrives(token.getAccessToken());
			for (Drive d : drives) {
				System.out.println(d.toString());
			}

			System.out.println("==============");
			System.out.print("READ:: OneDrive Drive Items");
			List<DriveItem> items = driveService.getRootItems(token.getAccessToken(), drives.get(0).id);
			for (DriveItem i : items) {
				System.out.println(i.toString());
			}

			scanner.close();					
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
