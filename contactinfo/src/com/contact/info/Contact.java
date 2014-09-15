package com.contact.info;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable
public class Contact 
{
	
	
	@PrimaryKey
	//@Persistent(valueStrategy=IdGeneratorStrategy.IDENTITY)
	private String contactKey;
	
	
	@Persistent
	private String userId;
	

	@Persistent
	private String contact_firstname;
	@Persistent
	private String contact_lastname;
	
	@Persistent
	private String contact_mobile_no;
	
	@Persistent
	private String contact_email_id;
	
	
	

	public String getContactKey() {
		return contactKey;
	}

	public void setContactKey(String contactKey) {
		this.contactKey = contactKey;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}



	public String getContact_firstname() {
		return contact_firstname;
	}

	public void setContact_firstname(String contact_firstname) {
		this.contact_firstname = contact_firstname;
	}

	public String getContact_lastname() {
		return contact_lastname;
	}

	public void setContact_lastname(String contact_lastname) {
		this.contact_lastname = contact_lastname;
	}

	public String getContact_mobile_no() {
		return contact_mobile_no;
	}

	public void setContact_mobile_no(String contact_mobile_no) {
		this.contact_mobile_no = contact_mobile_no;
	}

	public String getContact_email_id() {
		return contact_email_id;
	}

	public void setContact_email_id(String contact_email_id) {
		this.contact_email_id = contact_email_id;
	}

	
	
	
}
