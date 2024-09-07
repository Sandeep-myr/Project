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
@Table(name = "measurment_mstr")
public class MeasurmentMstrEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "MT_ID")
	private Long measurmentId;

	@Column(name = "MT_CODE")
	private String code;

	@Column(name = "MT_NAME")
	private String name;

	@Column(name = "MT_PRM_NAME")
	private String procureClassName;

	@ManyToOne
	@JoinColumn(name = "MT_CRM_ID", updatable = false)
	private ClientRegEntity clientId;

	@Column(name = "MT_STATUS")
	private Integer status;

	@ManyToOne
	@JoinColumn(name = "MT_CREATED_BY", updatable = false)
	private UserMstrEntity createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "MT_CREATED_ON", updatable = false)
	private Date createdOn;

	@ManyToOne
	@JoinColumn(name = "MT_UPDATED_BY")
	private UserMstrEntity updatedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "MT_UPDATED_ON")
	private Date updatedOn;
}
