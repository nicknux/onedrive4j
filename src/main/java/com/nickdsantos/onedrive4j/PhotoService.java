/**
 * Copyright (c) 2014 All Rights Reserved, nickdsantos.com
 */

package com.nickdsantos.onedrive4j;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nickdsantos.onedrive4j.Resource.SharedWith;

/**
 * @author Nick DS (me@nickdsantos.com)
 *
 */
public class PhotoService {
	static Logger logger = Logger.getLogger(AlbumService.class.getName());		
	
	public static final String API_HOST = "apis.live.net/v5.0";
	public static final String DEFAULT_SCHEME = "https";	
	public static final String ALBUM_URL_PATH = "/me/albums";
	
	/**
	 * This class should only be instantiated from the OneDrive.getPhotoService() method.
	 */
	protected PhotoService() {}
	
	/**
	 * Gets an array of Photo objects.
	 * @param accessToken
	 * @param albumId
	 * @return
	 * @throws IOException
	 */
	public Photo[] getPhotos(String accessToken, String albumId) throws IOException {
		ArrayList<Photo> photos = new ArrayList<Photo>();
		URI uri;
		try {			
			uri = new URIBuilder()
						.setScheme(DEFAULT_SCHEME)
						.setHost(API_HOST)
						.setPath("/" + albumId + "/files")
						.addParameter("access_token", accessToken)
						.build();
		} catch (URISyntaxException e) {			
			e.printStackTrace();
			throw new IllegalStateException("Invalid album path");
		}
		
		CloseableHttpClient httpClient = HttpClients.createDefault();			
		
		try {
			HttpGet httpGet = new HttpGet(uri);
			Map<Object, Object> rawResponse = httpClient.execute(httpGet, new OneDriveResponseHandler());
			if (rawResponse != null) {				
				List<Map<Object, Object>> rawResponseList = (List<Map<Object, Object>>) rawResponse.get("data");
				if (rawResponseList != null) {
					for (Map<Object, Object> respData : rawResponseList) {
						Photo p = createPhotoFromMap(respData);
						if (p != null) {							
							photos.add(p);
						}
					}
				}				
			}		
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			httpClient.close();
		}
		
		return photos.toArray(new Photo[photos.size()]);
	}
	
	/**
	 * Gets a specific Photo
	 * @param accessToken
	 * @param photoId
	 * @return
	 * @throws IOException
	 */
	public Photo getPhoto(String accessToken, String photoId) throws IOException {
		Photo photo = null;
		URI uri;
		try {			
			uri = new URIBuilder()
						.setScheme(DEFAULT_SCHEME)
						.setHost(API_HOST)
						.setPath("/" + photoId)
						.addParameter("access_token", accessToken)
						.build();
		} catch (URISyntaxException e) {			
			e.printStackTrace();
			throw new IllegalStateException("Invalid album path");
		}
						
		CloseableHttpClient httpClient = HttpClients.createDefault();
		
		try {
			HttpGet httpGet = new HttpGet(uri);			
			Map<Object, Object> rawResponse = httpClient.execute(httpGet, new OneDriveResponseHandler());
			if (rawResponse != null) {				
				photo = createPhotoFromMap(rawResponse);	
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			httpClient.close();
		}
		
		return photo;
	}
	
	/**
	 * Uploads an image to the given album. The returned Photo would only contain the new Photo Id
	 * and the automatically generated file name. To get the rest of the Photo's information,
	 * use the getPhoto() method.
	 * @param accessToken
	 * @param albumId
	 * @param format
	 * @param bytes
	 * @return
	 * @throws IOException
	 */
	public Photo uploadPhoto(String accessToken, String albumId, String format, byte[] bytes) throws IOException {
		Photo newPhoto = null;
		URI uri;
		try {			
			uri = new URIBuilder()
						.setScheme(DEFAULT_SCHEME)
						.setHost(API_HOST)
						.setPath("/" + albumId + "/files/" + UUID.randomUUID().toString() + "." + format)
						.addParameter("access_token", accessToken)
						.addParameter("downsize_photo_uploads", "false")
						.build();
		} catch (URISyntaxException e) {			
			e.printStackTrace();
			throw new IllegalStateException("Invalid album path");
		}
						
		CloseableHttpClient httpClient = HttpClients.createDefault();
		
		try {
			HttpPut httpPut = new HttpPut(uri);
			ByteArrayEntity imageEntity = new ByteArrayEntity(bytes);
			httpPut.setEntity(imageEntity);					
			
			Map<Object, Object> rawResponse = httpClient.execute(httpPut, new OneDriveResponseHandler());
			if (rawResponse != null) {
				//newPhoto = getPhoto(accessToken, rawResponse.get("id").toString());
				//newPhoto = createPhotoFromMap(rawResponse);
				// This will only return a "naked" photo object, with just the id and name populated
				newPhoto = new Photo();
				newPhoto.setName((String) rawResponse.get("name"));
				newPhoto.setId((String) rawResponse.get("id"));
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			httpClient.close();
		}				
		
		return newPhoto;
	}
	
	/**
	 * * Uploads an image to the given album. The returned Photo would only contain the new Photo Id
	 * and the automatically generated file name. To get the rest of the Photo's information,
	 * use the getPhoto() method.
	 * 
	 * This method also updates the photo's description.
	 * @param accessToken
	 * @param albumId
	 * @param format
	 * @param description
	 * @param bytes
	 * @return
	 * @throws IOException
	 */
	public Photo uploadPhoto(String accessToken, String albumId, String format, String description, byte[] bytes) throws IOException {
		Photo newPhoto = uploadPhoto(accessToken, albumId, format, bytes);		
		newPhoto = updatePhotoDescription(accessToken, newPhoto.getId(), description);
		
		return newPhoto;
	}
	
	/**
	 * Updates the Photo's description. Even if the OneDrive API supports updating the name, it's not supported in this client API library.
	 * Updating a Photo's name is actually updating its filename that could potentially null out other meta-data like camera's make and model.
	 * @param accessToken
	 * @param photoId
	 * @param description
	 * @return
	 * @throws IOException
	 */
	public Photo updatePhotoDescription(String accessToken, String photoId, String description) throws IOException {
		Photo updatedPhoto = null;
		URI uri;
		try {			
			uri = new URIBuilder()
						.setScheme(DEFAULT_SCHEME)
						.setHost(API_HOST)
						.setPath("/" + photoId)
						.build();
		} catch (URISyntaxException e) {			
			e.printStackTrace();
			throw new IllegalStateException("Invalid album path");
		}
						
		CloseableHttpClient httpClient = HttpClients.createDefault();
		
		try {
			Map<String, String> params = new HashMap<String, String>();
			params.put("description", description);
			
			final Gson gson = new GsonBuilder().create();
			String jsonString = gson.toJson(params);
			StringEntity jsonEntity = new StringEntity(jsonString);
			jsonEntity.setContentType(new BasicHeader("Content-Type", "application/json"));
			
			HttpPut httpPut = new HttpPut(uri);
			httpPut.setHeader("Authorization", "Bearer " + accessToken);
			httpPut.setEntity(jsonEntity);					
			
			Map<Object, Object> rawResponse = httpClient.execute(httpPut, new OneDriveResponseHandler());
			if (rawResponse != null) {
				updatedPhoto = createPhotoFromMap(rawResponse);
				// Do not get the photo id. revert to the original prior to the update
				updatedPhoto.setId(photoId);
			}	
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			httpClient.close();
		}
		
		return updatedPhoto;
	}		
	
	/**
	 * Deletes a specific Photo
	 * @param accessToken
	 * @param photoId
	 * @throws IOException
	 */
	public void deletePhoto(String accessToken, String photoId) throws IOException {
		URI uri;
		try {			
			uri = new URIBuilder()
						.setScheme(DEFAULT_SCHEME)
						.setHost(API_HOST)
						.setPath("/" + photoId)
						.addParameter("access_token", accessToken)
						.build();
		} catch (URISyntaxException e) {			
			e.printStackTrace();
			throw new IllegalStateException("Invalid photo path");
		}
						
		CloseableHttpClient httpClient = HttpClients.createDefault();			
		
		try {						
			HttpDelete httpDelete = new HttpDelete(uri);						
			Map<Object, Object> rawResponse = httpClient.execute(httpDelete, new OneDriveResponseHandler());
			if (rawResponse != null) {
				System.out.println(rawResponse);					
			}	
		} finally {
			httpClient.close();
		}
	}
	
	private Photo createPhotoFromMap(Map<Object, Object> responseMap) {
		for (Object k : responseMap.keySet()) {
			System.out.println(k + " : " + responseMap.get(k));
		}
		
		SimpleDateFormat dtFormat = new SimpleDateFormat("YYYY-MM-dd'T'HH:mm:ssZ");	
		Photo photo = null;
		if (responseMap.get("type").equals("photo")) {
			try {
				// Get embedded from JSON
				Map<String, String> fromUserMap = (Map<String, String>) responseMap.get("from");
				User fromUser = new User();
				fromUser.setId(fromUserMap.get("id"));
				fromUser.setName(fromUserMap.get("name"));
				
				// Get embedded shared_with JSON
				Map<String, String> sharedWithMap = (Map<String, String>) responseMap.get("shared_with");
				SharedWith sharedWith = SharedWith.parse(sharedWithMap.get("access"));		
				
				// Get embedded images JSON array
				List<Map<Object, Object>> imageMapList = (List<Map<Object, Object>>) responseMap.get("images");
				List<ImageItem> images = new ArrayList<ImageItem>();
				for (Map<Object, Object> imageMap : imageMapList) {
					ImageItem image = new ImageItem();
					image.setHeight((double) imageMap.get("height"));
					image.setWidth((double) imageMap.get("width"));
					image.setSource((String) imageMap.get("source"));
					image.setType((String) imageMap.get("type"));
					
					images.add(image);
				}
				
				// Get embedded location JSON
				Map<Object, Object> locationMap = (Map<Object, Object>) responseMap.get("location");
				Location location = null;
				if (locationMap != null) {
					location = new Location();
					location.setLongitude((double) locationMap.get("longitude"));
					location.setLatitude((double) locationMap.get("latitude"));
					location.setAltitude((double) locationMap.get("altitude"));
				}
				
				photo = new Photo();
				photo.setId((String) responseMap.get("id"));
				photo.setName((String) responseMap.get("name"));
				photo.setFrom(fromUser);
				photo.setDescription((String) responseMap.get("description"));
				photo.setParentId((String) responseMap.get("parent_id"));
				photo.setSize((double) responseMap.get("size"));
				photo.setCommentsCount((int) ((Double) responseMap.get("comments_count")).intValue());
				photo.setCommentsEnabled((boolean) responseMap.get("comments_enabled"));
				photo.setTagsCount((int) ((Double) responseMap.get("tags_count")).intValue());
				photo.setTagsEnabled((boolean) responseMap.get("tags_enabled"));
				photo.setIsEmbeddable((boolean) responseMap.get("is_embeddable"));
				photo.setLink((String) responseMap.get("picture"));
				photo.setLink((String) responseMap.get("source"));
				photo.setUploadLocation((String) responseMap.get("upload_location"));
				photo.setImages(images.toArray(new ImageItem[images.size()]));
				photo.setLink((String) responseMap.get("link"));	
				if (responseMap.get("when_taken") != null)
					photo.setWhenTaken(dtFormat.parse((String) responseMap.get("when_taken")));
				photo.setWidth((double) responseMap.get("width"));
				photo.setHeight((double) responseMap.get("height"));
				photo.setType((String) responseMap.get("type"));
				if (location != null)
					photo.setLocation(location);
				photo.setCameraMake((String) responseMap.get("camera_make"));
				photo.setCameraModel((String) responseMap.get("camera_model"));
				photo.setFocalLength((double) responseMap.get("focal_length"));
				photo.setFocalRatio((double) responseMap.get("focal_ratio"));
				photo.setExposureNumerator((double) responseMap.get("exposure_numerator"));
				photo.setExposureDenominator((double) responseMap.get("exposure_numerator"));
				photo.setSharedWith(sharedWith);
				photo.setCreatedTime(dtFormat.parse((String) responseMap.get("created_time")));
				photo.setUpdatedTime(dtFormat.parse((String) responseMap.get("updated_time")));
				photo.setClientUpdateTime(dtFormat.parse((String) responseMap.get("client_updated_time")));																
			} catch (ParseException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return photo;
	}
}
