/**
 * Copyright (c) 2014 All Rights Reserved, nickdsantos.com
 */

package com.nickdsantos.onedrive4j;

/**
 * @author Nick DS (me@nickdsantos.com)
 *
 */

public class AccessToken {
	private String _tokenType;
	private int _expiresIn;
	private String _scope;
	private String _accessToken;
	private String _refreshoken;
	private String _userId;
	
	public AccessToken(String tokenType, int expiresIn, String scope, String accessToken, String refreshToken,
					   String userId) {
		_tokenType = tokenType;
		_expiresIn = expiresIn;
		_scope = scope;
		_accessToken = accessToken;
		_refreshoken = refreshToken;
		_userId = userId;
	}
	
	/**
	 * @return the tokenType
	 */
	public String getTokenType() {
		return _tokenType;
	}
	
	/**
	 * @param tokenType the tokenType to set
	 */
	public void setTokenType(String tokenType) {
		_tokenType = tokenType;
	}
	
	/**
	 * @return the expiresIn
	 */
	public int getExpiresIn() {
		return _expiresIn;
	}
	
	/**
	 * @param expiresIn the expiresIn to set
	 */
	public void setExpiresIn(int expiresIn) {
		_expiresIn = expiresIn;
	}
	
	/**
	 * @return the scope
	 */
	public String getScope() {
		return _scope;
	}
	
	/**
	 * @param scope the scope to set
	 */
	public void setScope(String scope) {
		_scope = scope;
	}
	
	/**
	 * @return the accessToken
	 */
	public String getAccessToken() {
		return _accessToken;
	}

	/**
	 * @param accessToken the accessToken to set
	 */
	public void setAccessToken(String accessToken) {
		_accessToken = accessToken;
	}

	/**
	 * Gets the refresh token.
	 *
	 * @return the refresh token.
	 */
	public String getRefreshToken() {
		return _refreshoken;
	}

	/**
	 * @return the user Id
	 */
	public String getUserId() {
		return _userId;
	}
	
	/**
	 * @param userId the user Id  to set
	 */
	public void setUserId(String userId) {
		_userId = userId;
	}
	
}
