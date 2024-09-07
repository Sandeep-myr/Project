package com.rajutech.project.dao.model;

import java.time.LocalDateTime;
import java.util.Date;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
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

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "finance_center")
@GenericGenerator(name = "random_id", strategy = "com.rajutech.project.util.RandomIdGenerator")
public class FinanceCenterEntity {

    @Id
    @GeneratedValue(generator = "random_id")
    @Column(name = "FC_ID")
    private Long financeCenterId;

    @Column(name = "FC_COUNTRY_NAME")
    private String countryName;
    
    @Column(name = "FC_PROVISION_NAME")
    private String provinceName;
    
    @Column(name = "FC_COUNTRY_CODE")
    private String countryCode;
    
    @Column(name = "FC_PROVISION_CODE")
    private String provinceCode;
    
    @Temporal(TemporalType.DATE)
    @Column(name = "FC_EFFECTIVE_FROM")
    private Date effectiveFrom;
    
    @Column(name = "FC_STATUS")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "FC_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Column(name = "FC_CREATED_ON", updatable = false)
    private LocalDateTime createdOn;

    @ManyToOne
    @JoinColumn(name = "FC_CRM_ID", updatable = false)
    private ClientRegEntity clientId;

    @ManyToOne
    @JoinColumn(name = "FC_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Column(name = "FC_UPDATED_ON")
    private LocalDateTime updatedOn;

    @Column(name = "FC_CODE")
    private String financeCenterCode;
}