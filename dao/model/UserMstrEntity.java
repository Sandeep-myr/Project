package com.rajutech.project.dao.model;


import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_mstr")
public class UserMstrEntity {

	@Id
	@Column(name = "USR_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;

	@ManyToOne
	@JoinColumn(name = "USR_CREATED_BY", updatable = false)
	private UserMstrEntity createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "USR_CREATED_ON", updatable = false)
	private Date createdOn;

	@Column(name = "USR_DISPLAY_NAME")
	private String displayName;

	@Column(name = "USR_EMAIL", unique = true)
	private String email;

	@Column(name = "USR_EMP_CODE")
	private String empCode;

	@Column(name = "USR_EMP_DESG")
	private String empDesg;

	@Column(name = "USR_FIRST_NAME")
	private String firstName;

	@Column(name = "USR_LAST_NAME")
	private String lastName;

	@Column(name = "USR_MOBILE_NUM")
	private String mobileNo;

	@Column(name = "USR_PASSWORD")
	private String password;

	@Column(name = "USR_PHONE_NUM")
	private String phoneNo;

	@ManyToOne
	@JoinColumn(name = "USR_UPDATED_BY")
	private UserMstrEntity updatedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "USR_UPDATED_ON")
	private Date updatedOn;

	@Column(name = "USR_USER_NAME", unique = true)
	private String userName;

	@Column(name = "USR_STATUS")
	private int status;

	@Column(name = "USR_TYPE")
	private int userType;

	@Column(name = "USR_REMARKS")
	private String remarks;

//    @OneToOne
//    @JoinColumn(name = "USR_ERD_ID")
//    private EmpRegisterDtlEntityCopy empRegId;

	@ManyToOne
	@JoinColumn(name = "USR_CRM_ID", referencedColumnName = "CRM_ID", nullable = true)
	private ClientRegEntity clientRegEntity;

	@Column(name = "IS_CLIENT", columnDefinition = "int default 0")
	private boolean client;

//    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL, orphanRemoval = true)
//	private List<UserRoleMapEntityCopy> userRoleMstrs = new ArrayList<UserRoleMapEntityCopy>();

	@Column(name = "USR_ENCODED_PASSWORD")
	private String userEncodedPassword;
}

