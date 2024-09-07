package com.rajutech.project.request;

import lombok.Data;

@Data
public class ProjectPlantClassificationRequest {
	private Long projPlantClassId;
	private String asContractName;
	private Integer status;
	private Long plantClassificationId;
}
