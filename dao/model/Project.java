package com.rajutech.project.dao.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name="projectMstr")
public class Project {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long projectId;
	private String projectName;
	private Long centrallibId;
}
