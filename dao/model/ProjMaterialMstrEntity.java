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
@Table(name = "proj_material_classification_mstr")
@GenericGenerator(name = "random_id", strategy = "com.rajutech.project.util.RandomIdGenerator")
public class ProjMaterialMstrEntity implements Serializable {

    private static final long serialVersionUID = 571904159130869478L;

    @Id
	@GeneratedValue(generator = "random_id")
    @Column(name = "PMCM_ID")
    private Long materialClassificationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PMCM_MCM_ID")
    private MaterialClassMstrEntity groupId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PMCM_EPM_ID")
    private ProjMstrEntity projectId;

    @Column(name = "PMCM_INTRL_APPROVED")
    private Boolean intrlApproved;

    @Column(name = "PMCM_EXTRL_APPROVED")
    private Boolean extrlApproved;

    @Column(name = "PMCM_STATUS")
    private Integer status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PMCM_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PMCM_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PMCM_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PMCM_UPDATED_ON")
    private Date updatedOn;

}
