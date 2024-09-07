package com.rajutech.project.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProjWarehouseStockyardResponse {

	private Long wareHouseId;
	private String storeLocationCode;
	private String storeLocationName;
	private String category;
	private Integer status;
	private Boolean isAssigned;
	
	
}
