package com.rajutech.project.dao.model;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
@GenericGenerator(name = "random_id", strategy = "com.rajutech.project.util.RandomIdGenerator")
@Table(name = "proj_stock_pilled_mstr")
public class ProjStoreStockMstrEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
	@GeneratedValue(generator = "random_id")
    @Column(name = "SPM_ID")
    private Long stockMstrId;

    @Column(name = "SPM_DESC")
    private String desc;
    
    @Column(name = "SPM_CODE")
    private String stockCode;
    
    @Column(name = "SPM_STATUS")
    private Integer status;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SPM_EPM_ID")
    private ProjMstrEntity projectId;

    @Column(name = "SPM_CATEGORY")
    private String category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SPM_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "SPM_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SPM_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "SPM_UPDATED_ON")
    private Date updatedOn;

}
