package com.rajutech.project.request;

import lombok.Data;

@Data
public class ProjectEmployeeClassificationRequest {
	private Long projEmpClassId;
	private String asContractName;
	private String asWorkerUnionName;
	private String employeeCategory;
	private Integer status;
	private Long employeeClassificationId;
	

}
