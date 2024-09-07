package com.rajutech.project.dao.model;

import java.io.Serializable;
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
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "employee_classification_mstr")
@Setter
@Getter
public class EmpClassMstrEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ECM_ID")
    private Long empClassMstrId;

    @Column(name = "ECM_CODE")
    private String code;

    @ManyToOne
    @JoinColumn(name = "ECM_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ECM_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne
    @JoinColumn(name = "ECM_CRM_ID", updatable = false)
    private ClientRegEntity clientId;

    @Column(name = "ECM_NAME")
    private String name;

    @ManyToOne
    @JoinColumn(name = "ECM_MT_ID")
    private MeasurmentMstrEntity measurmentMstrEntity;

    @Column(name = "ECM_STATUS")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "ECM_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ECM_UPDATED_ON")
    private Date updatedOn;

}