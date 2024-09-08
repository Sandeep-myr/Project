package com.rajutech.project.request;

import lombok.Data;

@Data
public class WorkShiftRequestList {
	private Long workShiftId;
	private String workShiftName;
	private Integer startTime;
	private Integer endTime;
	
}
