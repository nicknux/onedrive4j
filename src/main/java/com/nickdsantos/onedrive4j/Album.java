/**
 * Copyright (c) 2014 All Rights Reserved, nickdsantos.com
 */

package com.nickdsantos.onedrive4j;

import java.util.Date;

/**
 * @author Nick DS (me@nickdsantos.com)
 *
 */
public class Album extends Resource {	
	private Date _clientUpdateTime;	
		
	/**
	 * @return the clientUpdateTime
	 */
	public Date getClientUpdateTime() {
		return _clientUpdateTime;
	}
	
	/**
	 * @param clientUpdateTime the clientUpdateTime to set
	 */
	public void setClientUpdateTime(Date clientUpdateTime) {
		_clientUpdateTime = clientUpdateTime;
	}
}
