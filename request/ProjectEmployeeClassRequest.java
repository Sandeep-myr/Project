package com.rajutech.project.request;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProjectEmployeeClassRequest {

	@NotNull(message = "Project ID cannot be null.")
	@Min(value = 1, message = "Project ID must be a positive number greater than zero.")
	private Long projectId;
	
	@Valid
	private List<ProjectEmployeeClassificationRequest> classificationList;
}
