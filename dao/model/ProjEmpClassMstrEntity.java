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
@Table(name = "proj_emp_classsfication_mstr")
public class ProjEmpClassMstrEntity implements Serializable {
    private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "random_id")
    @Column(name = "PEM_ID")
    private Long projEmpClassId;

    @ManyToOne
    @JoinColumn(name = "PEM_ECM_ID")
    private EmpClassMstrEntity empClassMstrEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PEM_EPM_ID")
    private ProjMstrEntity projectId;

    @Column(name = "PEM_EMPLOYEE_CATEGORY")
    private String projEmpCategory;

    @Column(name = "PEM_TRADE_CONTR_NAME")
    private String tradeContrName;

    @Column(name = "PEM_UNION_WORKER_NAME")
    private String unionName;

    @Column(name = "PEM_STATUS")
    private Integer status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PEM_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PEM_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PEM_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PEM_UPDATED_ON")
    private Date updatedOn;

}
