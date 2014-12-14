/**
 * Copyright (c) 2014 All Rights Reserved, nickdsantos.com
 */

package com.nickdsantos.onedrive4j;

import java.util.Date;

/**
 * @author Nick DS (me@nickdsantos.com)
 *
 */
public class Photo extends Resource{
	private double _size;
	private int _commentsCount;
	private boolean _commentsEnabled;
	private int _tagsCount;
	private boolean _tagsEnabled;
	private String _url;
	private String _picture;
	private String _downloadUrl;
	private ImageItem[] _images; // TODO:: returns various sizes; Change to size class later
	private Date _whenTaken;
	private double _height;
	private double _width;
	private Location _location; // TODO: change to location class later
	private String _cameraMake;
	private String _cameraModel;
	private double _focalRatio;
	private double _focalLength;
	private double _exposureNumerator;
	private double _exposureDenominator;
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
	
	/**
	 * @return the size
	 */
	public double getSize() {
		return _size;
	}
	
	/**
	 * @param size the size to set
	 */
	public void setSize(double size) {
		_size = size;
	}
	
	/**
	 * @return the commentsCount
	 */
	public int getCommentsCount() {
		return _commentsCount;
	}
	
	/**
	 * @param commentsCount the commentsCount to set
	 */
	public void setCommentsCount(int commentsCount) {
		_commentsCount = commentsCount;
	}
	
	/**
	 * @return the commentsEnabled
	 */
	public boolean isCommentsEnabled() {
		return _commentsEnabled;
	}
	
	/**
	 * @param commentsEnabled the commentsEnabled to set
	 */
	public void setCommentsEnabled(boolean commentsEnabled) {
		_commentsEnabled = commentsEnabled;
	}
	
	/**
	 * @return the tagsCount
	 */
	public int getTagsCount() {
		return _tagsCount;
	}
	
	/**
	 * @param tagsCount the tagsCount to set
	 */
	public void setTagsCount(int tagsCount) {
		_tagsCount = tagsCount;
	}
	
	/**
	 * @return the tagsEnabled
	 */
	public boolean isTagsEnabled() {
		return _tagsEnabled;
	}
	
	/**
	 * @param tagsEnabled the tagsEnabled to set
	 */
	public void setTagsEnabled(boolean tagsEnabled) {
		_tagsEnabled = tagsEnabled;
	}
	
	/**
	 * @return the url
	 */
	public String getUrl() {
		return _url;
	}
	
	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		_url = url;
	}
	
	/**
	 * @return the picture
	 */
	public String getPicture() {
		return _picture;
	}

	/**
	 * @param picture the picture to set
	 */
	public void setPicture(String picture) {
		_picture = picture;
	}

	/**
	 * @return the downloadUrl
	 */
	public String getDownloadUrl() {
		return _downloadUrl;
	}
	
	/**
	 * @param downloadUrl the downloadUrl to set
	 */
	public void setDownloadUrl(String downloadUrl) {
		_downloadUrl = downloadUrl;
	}
	
	/**
	 * @return the images
	 */
	public ImageItem[] getImages() {
		return _images;
	}
	
	/**
	 * @param images the images to set
	 */
	public void setImages(ImageItem[] images) {
		_images = images;
	}
	
	/**
	 * @return the whenTaken
	 */
	public Date getWhenTaken() {
		return _whenTaken;
	}
	
	/**
	 * @param whenTaken the whenTaken to set
	 */
	public void setWhenTaken(Date whenTaken) {
		_whenTaken = whenTaken;
	}
	
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
	 * @return the widht
	 */
	public double getWidth() {
		return _width;
	}
	
	/**
	 * @param widht the widht to set
	 */
	public void setWidth(double width) {
		_width = width;
	}
	
	/**
	 * @return the location
	 */
	public Location getLocation() {
		return _location;
	}
	
	/**
	 * @param location the location to set
	 */
	public void setLocation(Location location) {
		_location = location;
	}
	
	/**
	 * @return the cameraMake
	 */
	public String getCameraMake() {
		return _cameraMake;
	}
	
	/**
	 * @param cameraMake the cameraMake to set
	 */
	public void setCameraMake(String cameraMake) {
		_cameraMake = cameraMake;
	}
	
	/**
	 * @return the cameraModel
	 */
	public String getCameraModel() {
		return _cameraModel;
	}
	
	/**
	 * @param cameraModel the cameraModel to set
	 */
	public void setCameraModel(String cameraModel) {
		_cameraModel = cameraModel;
	}
	
	/**
	 * @return the focalRatio
	 */
	public double getFocalRatio() {
		return _focalRatio;
	}
	
	/**
	 * @param focalRatio the focalRatio to set
	 */
	public void setFocalRatio(double focalRatio) {
		_focalRatio = focalRatio;
	}
	
	/**
	 * @return the focalLength
	 */
	public double getFocalLength() {
		return _focalLength;
	}
	
	/**
	 * @param focalLength the focalLength to set
	 */
	public void setFocalLength(double focalLength) {
		_focalLength = focalLength;
	}
	
	/**
	 * @return the exposureNumerator
	 */
	public double getExposureNumerator() {
		return _exposureNumerator;
	}
	
	/**
	 * @param exposureNumerator the exposureNumerator to set
	 */
	public void setExposureNumerator(double exposureNumerator) {
		_exposureNumerator = exposureNumerator;
	}
	
	/**
	 * @return the exposureDenominator
	 */
	public double getExposureDenominator() {
		return _exposureDenominator;
	}
	
	/**
	 * @param exposureDenominator the exposureDenominator to set
	 */
	public void setExposureDenominator(double exposureDenominator) {
		_exposureDenominator = exposureDenominator;
	}
	
}
