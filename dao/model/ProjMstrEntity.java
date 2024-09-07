/**
 * @author 	SANJAYA SAHU
 * @eid 	10078
 * @version 2.0
 * @since	19-Aug-2024
 */
package com.rajutech.project.dao.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.GenericGenerator;

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
@Table(name = "erp_proj_mstr")
@GenericGenerator(name = "random_id", strategy = "com.rajutech.project.util.RandomIdGenerator")
public class ProjMstrEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "EPM_ID")
	private Long projectId;

	@Column(name = "EPM_CODE", length = 20)
	private String projectCode;

	@Column(name = "EPM_DESC", length = 50)
	private String projectName;

	@ManyToOne
	@JoinColumn(name = "EPM_CRM_ID", updatable = false)
	private ClientRegEntity clientRegEntity;

	// Load parent eagerly, we are getting parent most of the times to get EPS
	// details
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "EPM_PARENT_ID")
	private ProjMstrEntity parentProjectMstrEntity;

	@OneToMany(mappedBy = "parentProjectMstrEntity", cascade = CascadeType.ALL)
	private List<ProjMstrEntity> childEntities = new ArrayList<ProjMstrEntity>();

	@Column(name = "EPM_IS_PROJ")
	private boolean isProj;

	@Column(name = "EPM_IS_ASSIGNED", columnDefinition = "int default 0")
	private boolean assignedStatus;

	@ManyToOne
	@JoinColumn(name = "EPM_CREATED_BY", updatable = false)
	private UserMstrEntity createdBy;

	@Column(name = "EPM_CREATED_ON", updatable = false)
	private LocalDateTime createdOn;

	@ManyToOne
	@JoinColumn(name = "EPM_UPDATED_BY")
	private UserMstrEntity updatedBy;

	@Column(name = "EPM_UPDATED_ON")
	private LocalDateTime updatedOn;

	@Column(name = "EPM_STATUS")
	private Integer status;
}