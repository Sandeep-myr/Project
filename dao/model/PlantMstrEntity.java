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
@Table(name = "plant_classification_mstr")
@Getter
@Setter
public class PlantMstrEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PCM_ID")
    private Long plantClassifictaionId;

    @Column(name = "PCM_CODE")
    private String code;

    @ManyToOne
    @JoinColumn(name = "PCM_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PCM_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne
    @JoinColumn(name = "PCM_CRM_ID", updatable = false)
    private ClientRegEntity clientId;

    @Column(name = "PCM_NAME")
    private String name;

    @Column(name = "PCM_STATUS")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "PCM_MT_ID")
    private MeasurmentMstrEntity measurmentMstrEntity;

    @ManyToOne
    @JoinColumn(name = "PCM_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PCM_UPDATED_ON")
    private Date updatedOn;
}