package com.rajutech.project.dao.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

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
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "proj_general_values")
@GenericGenerator(name = "random_id", strategy = "com.rajutech.project.util.RandomIdGenerator")
public class ProjGeneralMstrEntity {

	@Id
	@GeneratedValue(generator = "random_id")
	@Column(name = "PGV_ID")
	private Long pgvId;

	@Column(name = "PGV_CONTRACT_NUM")
	private String contractNumber;

//	@ManyToOne(fetch = FetchType.LAZY)
	@ManyToOne
	@JoinColumn(name = "PGV_EPM_ID")
	@NotFound(action = NotFoundAction.IGNORE)
	private ProjMstrEntity projMstrEntity;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PGV_USR_ID")
	private UserMstrEntity respManager;

	@Column(name = "PGV_DFL_HRS")
	private Integer defualtHrs;

	@Column(name = "PGV_MAX_HRS")
	private Integer maxHrs;

	@Column(name = "PGV_ADDRESS")
	private String pgvAddress;

	@Column(name = "PGV_STATUS")
	private Integer status;

	@Column(name = "PGV_EFFECTIVE_FROM")
	private LocalDateTime effectiveFrom;

	@Column(name = "PGV_EFFECTIVE_TO")
	private LocalDateTime effectiveTo;

	@Column(name = "PGV_IS_LATEST")
	private String isLatest;

	@Column(name = "PGV_IS_DEFAULT")
	private String isDefault;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PGV_CREATED_BY", updatable = false)
	private UserMstrEntity createdBy;

	@Column(name = "PGV_CREATED_ON", updatable = false)
	private LocalDateTime createdOn;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PGV_UPDATED_BY")
	private UserMstrEntity updatedBy;

	@Column(name = "PGV_UPDATED_ON")
	private LocalDateTime updatedOn;

    @ManyToOne
    @JoinColumn(name = "PGV_GCAL_ID")
    private GlobalCalEntity globalCalEntity;

	@Column(name = "PGV_CON_CODE")
	private String isoAlpha3;

	@Column(name = "PGV_GEONAME_ID")
	private String geonameId;

	@Column(name = "PGV_CON_NAME")
	private String countryName;

	@Column(name = "PGV_PROVISION_NAME")
	private String provisionName;

	@Column(name = "PGV_CURRENCY")
	private String currency;

	@Column(name = "PGV_TIMEZONE")
	private String timezone;

	@Column(name = "PGV_CONTRACT_TYPE")
	private String contractType;

	@ManyToOne
	@JoinColumn(name = "PGV_FPC_ID")
	private ProfitCentreEntity profitCentreEntity;

	@ManyToOne
	@JoinColumn(name = "PGV_RC_ID")
	private ResourceCurveEntity projResourceCurveEntity;

	@ManyToOne
	@JoinColumn(name = "PGV_CMP_ID")
	private CompanyMstrEntity companyMstrEntity;

	@ManyToOne
	@JoinColumn(name = "PGV_MAIN_CONTRACT_COMPANY")
	private CompanyMstrEntity mainContractCompany;

	@Column(name = "PGV_DEF_STATUS")
	private String defaultStatus;

	@Column(name = "PGV_OVERCOST")
	private BigDecimal percentOverCost;

	@Column(name = "pgv_p6_integration_status")
	private String primaveraIntegration;

	@Column(name = "earned_hrs_source")
	private String earnedHourSource;

	@ManyToOne
	@JoinColumn(name = "PGV_FC_ID")
	private FinanceCenterEntity financeCentre;

	@Column(name = "PGV_LCONTRACTMILE_TYPE")
	private String lContractMile;

	@Column(name = "PGV_CPPTYPECONTRACT_TYPE")
	private String cppTypecontract;

	@Column(name = "PGV_SORCONTRACT_TYPE")
	private String sorContract;

	@Column(name = "PGV_CONTRACT_AWARD_DATE")
	private LocalDateTime contractAwardDate;

	@Column(name = "PGV_REPORTING_CURRENCY")
	private String reportingCurrency;

	@Column(name = "PGV_BASE_CUR_TO_REPORT_CUR")
	private BigDecimal baseCurcyToReportCurcy;
}
