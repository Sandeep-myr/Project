package com.rajutech.project.request;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class WarehouseStockyardRequest {
	
	@NotBlank(message = "projectId  is mandatory")
	@NotNull(message = "projectId  is Not Null")
	private Long projectId;
	private List<WarehouseStockyardListRequest> warehouseListRequest;

}
