/**
 * Copyright (c) 2014 All Rights Reserved, nickdsantos.com
 */

package com.nickdsantos.onedrive4j;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;

/**
 * @author Nick DS (me@nickdsantos.com)
 *
 */
public class OneDrive {
	static Logger logger = Logger.getLogger(OneDrive.class.getName());
	
	private static class AlbumServiceHolder {
		public static AlbumService _albumServiceInstance = new AlbumService();
	}
	
	private static class PhotoServiceHolder {
		public static PhotoService _photoServiceInstance = new PhotoService();
	}
	
	public static final String LOGIN_API_HOST = "login.live.com";
	public static final String DEFAULT_SCHEME = "https";	
	public static final String AUTHORIZE_URL_PATH = "/oauth20_authorize.srf";
	public static final String ACCESS_TOKEN_URL_PATH = "/oauth20_token.srf";
	
	private String _clientId;
	private String _clientSecret;
	private String _callback;		
	
	public OneDrive(String clientId, String clientSecret, String callback) {
		_clientId = clientId;
		_clientSecret = clientSecret;
		_callback = callback;
	}
	
	public AlbumService getAlbumService() {
		return AlbumServiceHolder._albumServiceInstance;
	}
	
	public PhotoService getPhotoService() {
		return PhotoServiceHolder._photoServiceInstance;
	}
	
	public String authorize(Scope[] scopes) {				
		StringBuilder sbScopes = new StringBuilder();
		for (Scope s : scopes) {
			sbScopes.append(s).append(" ");
		}
		
		String authzUrl = null;
		try {
			URI uri = new URIBuilder()
						.setScheme(DEFAULT_SCHEME)
						.setHost(LOGIN_API_HOST)
						.setPath(AUTHORIZE_URL_PATH)
						.setParameter("client_id", _clientId)
						.setParameter("scope",sbScopes.toString())
						.setParameter("response_type", "code")
						.setParameter("redirect_uri", _callback)
						.build();
			
			authzUrl = uri.toString();
		} catch (URISyntaxException e) {			
			e.printStackTrace();
			throw new IllegalStateException("Invalid authorization url");
		}		
				
		return authzUrl;
	}
	
	public AccessToken getAccessToken(String authorizationCode) throws IOException {
		AccessToken accessToken = null;
		URI uri;
		try {
			uri = new URIBuilder()
						.setScheme(DEFAULT_SCHEME)
						.setHost(LOGIN_API_HOST)
						.setPath(ACCESS_TOKEN_URL_PATH)
						.build();
		} catch (URISyntaxException e) {			
			e.printStackTrace();
			throw new IllegalStateException("Invalid access token path");
		}
				
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("client_id", _clientId));
		params.add(new BasicNameValuePair("redirect_uri", _callback));
		params.add(new BasicNameValuePair("client_secret", _clientSecret));
		params.add(new BasicNameValuePair("code", authorizationCode));
		params.add(new BasicNameValuePair("grant_type", "authorization_code"));
		UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(params, Consts.UTF_8);
		
		CloseableHttpClient httpClient = HttpClients.createDefault();
		try {
			HttpPost httpPost = new HttpPost(uri);
			httpPost.setEntity(formEntity);					
			
			Map<Object, Object> rawToken = httpClient.execute(httpPost, new OneDriveResponseHandler());
			if (rawToken != null) {
				accessToken = new AccessToken(
						rawToken.get("token_type").toString(),
						(int) Double.parseDouble(rawToken.get("expires_in").toString()),
						rawToken.get("scope").toString(),
						rawToken.get("access_token").toString(),
						rawToken.get("authentication_token").toString());
			}
		} finally {
			httpClient.close();
		}
		
		return accessToken;
	}	
}
