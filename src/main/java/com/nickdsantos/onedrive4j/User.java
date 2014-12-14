/**
 * Copyright (c) 2014 All Rights Reserved, nickdsantos.com
 */

package com.nickdsantos.onedrive4j;

/**
 * @author Nick DS (me@nickdsantos.com)
 *
 */
public class User {
	private String _name;
	private String _id;
	private String _access;
	
	/**
	 * @return the name
	 */
	public String getName() {
		return _name;
	}
	
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		_name = name;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return _id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		_id = id;
	}

	/**
	 * @return the access
	 */
	public String getAccess() {
		return _access;
	}

	/**
	 * @param access the access to set
	 */
	public void setAccess(String access) {
		_access = access;
	}
}
