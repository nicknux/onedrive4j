/**
 * Copyright (c) 2014 All Rights Reserved, nickdsantos.com
 */

package com.nickdsantos.onedrive4j;

/**
 * @author Nick DS (me@nickdsantos.com)
 *
 */
public class ImageItem {
	private double _height;
	private double _width;
	private String _source;
	private String _type;
	
	/**
	 * @return the height
	 */
	public double getHeight() {
		return _height;
	}
	
	/**
	 * @param height the height to set
	 */
	public void setHeight(double height) {
		_height = height;
	}
	
	/**
	 * @return the width
	 */
	public double getWidth() {
		return _width;
	}
	
	/**
	 * @param width the width to set
	 */
	public void setWidth(double width) {
		_width = width;
	}
	
	/**
	 * @return the source
	 */
	public String getSource() {
		return _source;
	}
	
	/**
	 * @param source the source to set
	 */
	public void setSource(String source) {
		_source = source;
	}
	
	/**
	 * @return the type
	 */
	public String getType() {
		return _type;
	}
	
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		_type = type;
	}	
}
