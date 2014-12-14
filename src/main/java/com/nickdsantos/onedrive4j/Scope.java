/**
 * Copyright (c) 2014 All Rights Reserved, nickdsantos.com
 */

package com.nickdsantos.onedrive4j;

/**
 * @author Nick DS (me@nickdsantos.com)
 *
 */
public enum Scope {
	// Core Scopes
	BASIC("wl.basic"),
	OFFLINE_ACCESS("wl.offline_access"),
	SIGNIN("wl.signin"),
	
	// Extended Scopes
	PHOTOS("wl.photos"),
	SKYDRIVE("wl.skydrive"),
	SKYDRIVE_UPDATE("wl.skydrive_update");
	
	private final String _val;
	private Scope(String val) {
		_val = val;
	}
	
	@Override
	public String toString() {
		return _val;
	}
	
	public static Scope parse(String value) throws Exception {
		switch (value.toLowerCase()) {
			case "wl.basic":
				return Scope.BASIC;
			case "wl.offline_access":
				return Scope.OFFLINE_ACCESS;
			case "wl.signin":
				return Scope.SIGNIN;
			case "wl.photos":
				return Scope.PHOTOS;
			case "wl.skydrive":
				return Scope.SKYDRIVE;
			case "wl.skydrive_update":
				return Scope.SKYDRIVE_UPDATE;
			default:
				throw new Exception("Unsupported value: " + value);
		}
	}
}
