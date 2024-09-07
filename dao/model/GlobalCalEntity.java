package com.rajutech.project.dao.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
@Table(name = "global_calendar")
public class GlobalCalEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "GCAL_ID")
	private Long globalCalId;

	@Column(name = "GCAL_NAME")
	private String globalCalname;

	@Column(name = "GCAL_DEFAULT_VALUE")
	private Integer calDefaultValue;

	@ManyToOne
	@JoinColumn(name = "GCAL_CRM_ID", updatable = false)
	private ClientRegEntity clientId;

	@Column(name = "GCAL_IS_LATEST")
	private boolean latest;

	@Column(name = "GCAL_FROM_DATE")
	private LocalDateTime fromDate;

	@Column(name = "GCAL_TO_DATE")
	private LocalDateTime toDate;

	@Column(name = "GCAL_STATUS")
	private Integer status;

	@ManyToOne
	@JoinColumn(name = "GCAL_CREATED_BY", updatable = false)
	private UserMstrEntity createdBy;

	@Column(name = "GCAL_CREATED_ON", updatable = false)
	private LocalDateTime createdOn;

	@ManyToOne
	@JoinColumn(name = "GCAL_UPDATED_BY")
	private UserMstrEntity updatedBy;

	@Column(name = "GCAL_UPDATED_ON")
	private LocalDateTime updatedOn;

	@Column(name = "CAL_TYPE")
	private String calType;

	@ManyToOne
	@JoinColumn(name = "PCAL_EPM_ID")
	private ProjMstrEntity projMstrEntity;

	private transient boolean isProjectAssigned;
}
