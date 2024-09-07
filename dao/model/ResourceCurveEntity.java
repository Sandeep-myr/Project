package com.rajutech.project.dao.model;

import java.io.Serializable;
import java.math.BigDecimal;
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

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "resource_curves")
public class ResourceCurveEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RC_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "RC_CRM_ID", updatable = false)
    private ClientRegEntity clientId;

    @Column(name = "RC_TYPE")
    private String curveType;

    @Column(name = "RC_CATG")
    private String catg;

    @Column(name = "RC_DEFAULT", columnDefinition = "int default 0")
    private boolean defaultFlag;

    @Column(name = "RC_RANGE1")
    private BigDecimal range1;

    @Column(name = "RC_RANGE2")
    private BigDecimal range2;

    @Column(name = "RC_RANGE3")
    private BigDecimal range3;

    @Column(name = "RC_RANGE4")
    private BigDecimal range4;

    @Column(name = "RC_RANGE5")
    private BigDecimal range5;

    @Column(name = "RC_RANGE6")
    private BigDecimal range6;

    @Column(name = "RC_RANGE7")
    private BigDecimal range7;

    @Column(name = "RC_RANGE8")
    private BigDecimal range8;

    @Column(name = "RC_RANGE9")
    private BigDecimal range9;

    @Column(name = "RC_RANGE10")
    private BigDecimal range10;

    @Column(name = "RC_TOTAL")
    private BigDecimal total;

    @Column(name = "RC_STATUS")
    private int status;

    @ManyToOne
    @JoinColumn(name = "RC_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "RC_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne
    @JoinColumn(name = "RC_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "RC_UPDATED_ON")
    private Date updatedOn;


}
