package com.rajutech.project.response;

import lombok.Builder;
import lombok.Data;

@Data
public class ProjectPlantClassificationResponse {
	
	private Long projPlantClassId;
	private String resourceCode;
	private String resourceName;
	private String asContractName;
	private String unitOfMeasureName;
	private Long plantClassificationId;
	private Integer status;
	private Boolean isAssigned;
	

}
