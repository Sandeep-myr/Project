package com.rajutech.project.response;

import lombok.Data;

@Data
public class ProjectEmployeeClassificationResponse {
	
	private Long projEmpClassId;
	private String resourceCode;
	private String resourceName;
	private String asContractName;
	private String asWorkerUnionName;
	private String employeeCategory;
	private String unitOfMeasureName;
	private Long empClassificationId;
	private Integer status;
	private Boolean isAssigned;
	
	

}
