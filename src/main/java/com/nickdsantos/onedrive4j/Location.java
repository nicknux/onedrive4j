/**
 * Copyright (c) 2014 All Rights Reserved, nickdsantos.com
 */

package com.nickdsantos.onedrive4j;

/**
 * @author Nick DS (me@nickdsantos.com)
 *
 */
public class Location {
	private double _longitude;
	private double _latitude;
	private double _altitude;
	
	/**
	 * @return the longitude
	 */
	public double getLongitude() {
		return _longitude;
	}
	
	/**
	 * @param longitude the longitude to set
	 */
	public void setLongitude(double longitude) {
		_longitude = longitude;
	}
	
	/**
	 * @return the latitude
	 */
	public double getLatitude() {
		return _latitude;
	}
	
	/**
	 * @param latitude the latitude to set
	 */
	public void setLatitude(double latitude) {
		_latitude = latitude;
	}
	
	/**
	 * @return the altitude
	 */
	public double getAltitude() {
		return _altitude;
	}
	
	/**
	 * @param altitude the altitude to set
	 */
	public void setAltitude(double altitude) {
		_altitude = altitude;
	}
}
