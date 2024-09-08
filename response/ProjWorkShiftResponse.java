package com.rajutech.project.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProjWorkShiftResponse {
	private Long workShiftId;
	private String shiftName;
	private Integer startHour;
	private Integer duration;
	private Integer status;
	private Boolean isAssigned;

}
