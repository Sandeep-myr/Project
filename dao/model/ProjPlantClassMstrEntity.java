package com.rajutech.project.dao.model;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
@GenericGenerator(name = "random_id", strategy = "com.rajutech.project.util.RandomIdGenerator")
@Table(name = "proj_plant_classification_mstr")
public class ProjPlantClassMstrEntity implements Serializable {

	private static final long serialVersionUID = -7469582437210724116L;

	@Id
	@GeneratedValue(generator = "random_id")
	@Column(name = "PPC_ID")
	private Long plantClassId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PPC_EPM_ID")
	private ProjMstrEntity projId;

	@ManyToOne
	@JoinColumn(name = "PPC_PCM_ID")
	private PlantMstrEntity plantMstrEntity;

	@Column(name = "PPC_PLANT_CONTR_NAME")
	private String plantContrName;

	@Column(name = "PPC_STATUS")
	private Integer status;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PPC_CREATED_BY", updatable = false)
	private UserMstrEntity createdBy;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PPC_UPDATED_BY")
	private UserMstrEntity updatedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "PPC_CREATED_ON", updatable = false)
	private Date createdOn;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "PPC_UPDATED_ON")
	private Date updatedOn;

}
