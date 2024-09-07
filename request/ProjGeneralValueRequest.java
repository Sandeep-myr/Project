/**
 * @author 	SANJAYA SAHU
 * @eid 	10078
 * @version 2.0
 * @since	19-Aug-2024
 */
package com.rajutech.project.request;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Validated
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjGeneralValueRequest {

	private Long pgvId;

	private String contractNumber;

	@NotNull
	private Long projectId;

	@NotNull
	private Long respManager;

	private Integer defualtHrs;

	private Integer maxHrs;

	private String pgvAddress;

	private LocalDateTime effectiveFrom;

	private LocalDateTime effectiveTo;

	private String isLatest;

	private String isDefault;
	
	private Long globalCalId;

	private String isoAlpha3;

	private String geonameId;

	private String countryName;

	private String provisionName;

	private String currency;

	private String timezone;

	private String contractType;

	@NotNull
	private Long profitCentreId;

	@NotNull
	private Long projResourceCurveId;

	@NotNull
	private Long companyMstrId;

	@NotNull
	private Long mainContractCompany;

	private String defaultStatus;

	private BigDecimal percentOverCost;

	private String primaveraIntegration;

	private String earnedHourSource;

	@NotNull
	private Long financeCentreId;

	private String lContractMile;

	private String cppTypecontract;

	private String sorContract;

	private LocalDateTime contractAwardDate;

	private String reportingCurrency;

	private BigDecimal baseCurcyToReportCurcy;
}
