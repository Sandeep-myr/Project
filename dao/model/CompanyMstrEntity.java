package com.rajutech.project.dao.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "company_mstr")
@GenericGenerator(name = "random_id", strategy = "com.rajutech.project.util.RandomIdGenerator")
public class CompanyMstrEntity {

	@Id
	@GeneratedValue(generator = "random_id")
	@Column(name = "CMP_ID")
	private Long companyMstrId;

	@Column(name = "CMP_CODE", nullable = false)
	private String companyCode;

	@Column(name = "CMP_NAME", nullable = false)
	private String companyName;

	@Column(name = "CMP_CATEGORY", nullable = false)
	private String companyCategory;

	@Column(name = "CMP_BUSINESS_CATEGORY", nullable = false)
	private String businessCategory;

	@Column(name = "CMP_ACTIVITY")
	private String businessActivity;

	@Column(name = "CMP_BUSINESS_NUM", nullable = false)
	private String businessNumber;

	@Column(name = "CMP_REG_NUM", nullable = false)
	private String companyNumber;

	@Column(name = "CMP_TAX_FILE_NUM", nullable = false)
	private String taxFileNo;

	@Column(name = "CMP_STATUS", nullable = false)
	private Integer status;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CMP_CREATED_BY", updatable = false)
	private UserMstrEntity createdBy;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CMP_UPDATED_BY")
	private UserMstrEntity updatedBy;
	
	@Column(name = "CMP_CREATED_ON", updatable = false)
	private LocalDateTime createdOn;
	
	@Column(name = "CMP_UPDATED_ON")
	private LocalDateTime updatedOn;
	
	
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "CMP_CRM_ID", updatable = false)
//	private ClientRegEntity clientId;
	
//	@OneToMany(mappedBy = "companyMstrEntity", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
//	private List<CmpAddressEntity> cmpAddressEntities = new ArrayList<CmpAddressEntity>();
//
//	@OneToMany(mappedBy = "companyMstrEntity", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
//	private List<CmpContactsEntity> cmpContactsEntities = new ArrayList<CmpContactsEntity>();
//
//	@OneToMany(mappedBy = "companyMstrEntity", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
//	private List<CmpCurrentProjsEntity> cmpCurrentProjsEntities = new ArrayList<CmpCurrentProjsEntity>();
//
//	@OneToMany(mappedBy = "companyMstrEntity", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
//	private List<CmpBankAccountEntity> cmpBankAccountEntities = new ArrayList<CmpBankAccountEntity>();
//
//	@OneToMany(mappedBy = "companyMstrEntity", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
//	private List<CompanyETCMstrEntity> cmpETCEntities = new ArrayList<CompanyETCMstrEntity>();


//	@Column(name = "CMP_COUNTRY_NAME")
//	private String countryName;
//
//	@Column(name = "CMP_COUNTRY_CODE")
//	private String countryCode;
//
//	@Column(name = "CMP_COUNTRY_PROVINCE")
//	private String countryProvince;
	
}
