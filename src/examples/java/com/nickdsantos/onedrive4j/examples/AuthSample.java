/**
 * Copyright (c) 2014 All Rights Reserved, nickdsantos.com
 */

package com.nickdsantos.onedrive4j.examples;

import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;

import com.nickdsantos.onedrive4j.AccessToken;
import com.nickdsantos.onedrive4j.OneDrive;
import com.nickdsantos.onedrive4j.Scope;


/**
 * @author Nick DS (me@nickdsantos.com)
 *
 */
public class AuthSample extends BaseSample {
	public static void main(String[] args) throws IOException {
		Properties prop = loadApiSettings();
		OneDrive oneDrive = new OneDrive(prop.getProperty("client_id"), prop.getProperty("client_secret"), prop.getProperty("callback_url"));
		String authzUrl = oneDrive.authorize(new Scope[] { Scope.SKYDRIVE_UPDATE,  Scope.PHOTOS });
		System.out.println(authzUrl);
		
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
			scanner.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
