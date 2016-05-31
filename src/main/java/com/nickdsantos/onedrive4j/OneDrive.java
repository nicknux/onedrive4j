/**
 * Copyright (c) 2014 All Rights Reserved, nickdsantos.com
 */

package com.nickdsantos.onedrive4j;

import com.google.gson.Gson;
import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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

	private static class DriveServiceHolder {
		public static DriveService _driveServiceInstance = new DriveService();
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

	public DriveService getDriveService() {
		return DriveServiceHolder._driveServiceInstance;
	}
	
	public String authorize(Scope[] scopes) {				
		StringBuilder sbScopes = new StringBuilder();
		for (Scope s : scopes) {
			sbScopes.append(s).append(" ");
		}
		
		String authzUrl;
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
			throw new IllegalStateException("Invalid authorization url", e);
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
			throw new IllegalStateException("Invalid access token path", e);
		}
				
		List<NameValuePair> params = new ArrayList<>();
		params.add(new BasicNameValuePair("client_id", _clientId));
		params.add(new BasicNameValuePair("redirect_uri", _callback));
		params.add(new BasicNameValuePair("client_secret", _clientSecret));
		params.add(new BasicNameValuePair("code", authorizationCode));
		params.add(new BasicNameValuePair("grant_type", "authorization_code"));
		UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(params, Consts.UTF_8);

		try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
			HttpPost httpPost = new HttpPost(uri);
			httpPost.setEntity(formEntity);

			Map<Object, Object> rawToken = httpClient.execute(httpPost, new OneDriveJsonToMapResponseHandler());
			if (rawToken != null) {
				accessToken = new AccessToken(
						rawToken.get("token_type").toString(),
						(int) Double.parseDouble(rawToken.get("expires_in").toString()),
						rawToken.get("scope").toString(),
						rawToken.get("access_token").toString(),
						Objects.toString(rawToken.get("refresh_token"), null),
						rawToken.get("user_id").toString());
			}
		}
		
		return accessToken;
	}

	/**
	 * Gets a new access token from a previously acquired refresh token.
	 *
	 * @param refreshToken the refresh token.
	 * @return the access token.
	 * @throws IOException if an error occurs.
     */
	public AccessToken getAccessTokenFromRefreshToken(String refreshToken) throws IOException {
		AccessToken accessToken = null;
		URI uri;
		try {
			uri = new URIBuilder()
						.setScheme(DEFAULT_SCHEME)
						.setHost(LOGIN_API_HOST)
						.setPath(ACCESS_TOKEN_URL_PATH)
						.build();
		} catch (URISyntaxException e) {
			throw new IllegalStateException("Invalid access token path", e);
		}

		List<NameValuePair> params = new ArrayList<>();
		params.add(new BasicNameValuePair("client_id", _clientId));
		params.add(new BasicNameValuePair("redirect_uri", _callback));
		params.add(new BasicNameValuePair("client_secret", _clientSecret));
		params.add(new BasicNameValuePair("refresh_token", refreshToken));
		params.add(new BasicNameValuePair("grant_type", "refresh_token"));
		UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(params, Consts.UTF_8);

		try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
			HttpPost httpPost = new HttpPost(uri);
			httpPost.setEntity(formEntity);

			Map<Object, Object> rawToken = httpClient.execute(httpPost, new OneDriveJsonToMapResponseHandler());
			if (rawToken != null) {
				accessToken = new AccessToken(
						rawToken.get("token_type").toString(),
						(int) Double.parseDouble(rawToken.get("expires_in").toString()),
						rawToken.get("scope").toString(),
						rawToken.get("access_token").toString(),
						Objects.toString(rawToken.get("refresh_token"), null),
						Objects.toString(rawToken.get("user_id"), null));
			}
		}

		return accessToken;
	}

	/**
	 * Gets the details about the current user.
	 *
	 * @param accessToken the access token.
	 * @return the user's details.
	 * @throws IOException if an error occurs.
	 */
	public Me getMe(String accessToken) throws IOException {
		URI uri;
		try {
			uri = new URIBuilder()
					.setScheme(DEFAULT_SCHEME)
					.setHost("apis.live.net/v5.0")
					.setPath("/me")
					.addParameter("access_token", accessToken)
					.build();
		} catch (URISyntaxException e) {
			throw new IllegalStateException("Invalid drives path", e);
		}

		try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
			HttpGet httpGet = new HttpGet(uri);
			String rawResponse = httpClient.execute(httpGet, new OneDriveStringResponseHandler());
			return new Gson().fromJson(rawResponse, Me.class);
		} catch (Exception e) {
			throw new IOException("Error getting drives", e);
		}
	}
}
