package com.rajutech.project.dao.model;

/**
* @author  Prakash Kumar Sahoo
* @version 2.0
* @since   03-09-2024
*/

public class LoggedInUserDetails {
	
	private Long id;

	private Long userId;
	
	private String userName;
	
	private Long crmId;
	
	private String crmCode;
	
	private String email;
	
	private String designation;

	private String jwt;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Long getCrmId() {
		return crmId;
	}

	public void setCrmId(Long crmId) {
		this.crmId = crmId;
	}

	public String getCrmCode() {
		return crmCode;
	}

	public void setCrmCode(String crmCode) {
		this.crmCode = crmCode;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getJwt() {
		return jwt;
	}

	public void setJwt(String jwt) {
		this.jwt = jwt;
	}
	
}
