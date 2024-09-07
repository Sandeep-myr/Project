package com.rajutech.project.dao.model;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "proj_shifts_mstr")
public class ProjWorkShiftMstrEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SHF_ID")
    private Long id;

    @Column(name = "SHF_CODE")
    private String code;

    @Column(name = "SHF_EPM_ID")
    private Long projectId;

    @Column(name = "SHF_FINISH_TIME")
    private Time finishTime;

    @Column(name = "SHF_START_TIME")
    private Time startTime;

    @Column(name = "SHF_START_HOURS")
    private Integer startHours;

    @Column(name = "SHF_START_MINTUES")
    private Integer startMinutes;

    @Column(name = "SHF_FINISH_HOURS")
    private Integer finishHours;

    @Column(name = "SHF_FINISH_MINTUES")
    private Integer finishMinutes;

    @Column(name = "SHF_STATUS")
    private Integer status;

    @Column(name = "SHF_CREATED_BY", updatable = false)
    private String createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "SHF_CREATED_ON", updatable = false)
    private Date createdOn;

    @Column(name = "SHF_UPDATED_BY")
    private String updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "SHF_UPDATED_ON")
    private Date updatedOn;

}
