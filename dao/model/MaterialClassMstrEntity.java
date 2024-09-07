package com.rajutech.project.dao.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "material_classification_mstr")
public class MaterialClassMstrEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MCM_ID")
    private Long id;

    @Column(name = "MCM_CODE")
    private String code;

    @Column(name = "MCM_NAME")
    private String name;

    @ManyToOne
    @JoinColumn(name = "MCM_CRM_ID", updatable = false)
    private ClientRegEntity clientId;

    @Column(name = "MCM_IS_ITEM")
    private boolean item;

    @ManyToOne
    @JoinColumn(name = "MCM_MT_ID")
    private MeasurmentMstrEntity measurmentMstrEntity;

    @ManyToOne
    @JoinColumn(name = "MCM_PARENT_ID")
    private MaterialClassMstrEntity materialClassMstrEntity;

    @OneToMany(mappedBy = "materialClassMstrEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<MaterialClassMstrEntity> childEntities = new ArrayList<MaterialClassMstrEntity>();

//    @OneToMany(mappedBy = "materialClassMstrEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private List<ProcureCatgDtlEntity> procureCatgDtlEntities = new ArrayList<ProcureCatgDtlEntity>();

    @Column(name = "MCM_STATUS")
    private Integer status;
    
    @Column(name = "MCM_ACCOUNTCLASSIFICATION")
    private String accountClassification;

    @ManyToOne
    @JoinColumn(name = "MT_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "MCM_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne
    @JoinColumn(name = "MCM_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "MCM_UPDATED_ON")
    private Date updatedOn;

}
