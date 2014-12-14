/**
 * Copyright (c) 2014 All Rights Reserved, nickdsantos.com
 */

package com.nickdsantos.onedrive4j;

import java.util.Date;

/**
 * @author Nick DS (me@nickdsantos.com)
 *
 */
public class Resource {
	public enum SharedWith {
		Selected("People I selected"), 
		JustMe("Just me"), 
		Everyone("Everyone (public)"), 
		Friends("Friends"), 
		FriendsOfFriends("My friends and their friends"), 
		PeopleWithLink("People with a link");
		
		private final String _val;
		private SharedWith(String val) {
			_val = val;			
		}
		
		@Override
		public String toString() {
			return _val;			
		}
		
		public static SharedWith parse(String value) throws Exception {
			switch (value.toLowerCase()) {
				case "people i selected":
					return SharedWith.Selected;
				case "just me":
					return SharedWith.JustMe;
				case "everyone (public)":
					return SharedWith.Everyone;
				case "friends":
					return SharedWith.Friends;
				case "my friends and their friends":
					return SharedWith.FriendsOfFriends;
				case "people with a link":
					return SharedWith.PeopleWithLink;
				default:
					throw new Exception("Unsupported value: " + value);
			}
		}
	}
	
	private String _id;
	private User _from;
	private String _name;
	private String _description;
	private String _parentId;
	private boolean _isEmbeddable;
	private String _uploadLocation;
	private String _link;
	private String _type;
	private SharedWith _sharedWith;
	private Date _createdTime;
	private Date _updatedTime;
	
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
	 * @return the from
	 */
	public User getFrom() {
		return _from;
	}
	
	/**
	 * @param from the from to set
	 */
	public void setFrom(User from) {
		_from = from;
	}
	
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
	 * @return the description
	 */
	public String getDescription() {
		return _description;
	}
	
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		_description = description;
	}
	
	/**
	 * @return the parentId
	 */
	public String getParentId() {
		return _parentId;
	}
	
	/**
	 * @param parentId the parentId to set
	 */
	public void setParentId(String parentId) {
		_parentId = parentId;
	}
	
	/**
	 * @return the uploadLocation
	 */
	public String getUploadLocation() {
		return _uploadLocation;
	}
	
	/**
	 * @param uploadLocation the uploadLocation to set
	 */
	public void setUploadLocation(String uploadLocation) {
		_uploadLocation = uploadLocation;
	}
	
	/**
	 * @return the isEmbeddable
	 */
	public boolean isEmbeddable() {
		return _isEmbeddable;
	}
	
	/**
	 * @param isEmbeddable the isEmbeddable to set
	 */
	public void setIsEmbeddable(boolean isEmbeddable) {
		_isEmbeddable = isEmbeddable;
	}
	
	/**
	 * @return the link
	 */
	public String getLink() {
		return _link;
	}
	
	/**
	 * @param link the link to set
	 */
	public void setLink(String link) {
		_link = link;
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
	
	/**
	 * @return the shared_with
	 */
	public SharedWith getSharedWith() {
		return _sharedWith;
	}
	
	/**
	 * @param shared_with the shared_with to set
	 */
	public void setSharedWith(SharedWith sharedWith) {
		_sharedWith = sharedWith;
	}
	
	/**
	 * @return the createdTime
	 */
	public Date getCreatedTime() {
		return _createdTime;
	}
	
	/**
	 * @param createdTime the createdTime to set
	 */
	public void setCreatedTime(Date createdTime) {
		_createdTime = createdTime;
	}
	
	/**
	 * @return the updatedTime
	 */
	public Date getUpdatedTime() {
		return _updatedTime;
	}
	
	/**
	 * @param updatedTime the updatedTime to set
	 */
	public void setUpdatedTime(Date updatedTime) {
		_updatedTime = updatedTime;
	}
	
	@Override
	public String toString() {
		// TODO: return a proper JSON
		return "Id: " + _id + "; Name: " + _name + "; Description: " + _description;	
	}
}
