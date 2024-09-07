package com.rajutech.project.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class WarehouseStockyardListRequest {

	private Long wareHouseId; // Optional for updates; no validation needed

    @NotBlank(message = "Warehouse code is mandatory")
    @Size(max = 20, message = "Warehouse code must be less than or equal to 20 characters")
    private String wareHouseCode;

    @NotBlank(message = "Warehouse description is mandatory")
    @Size(max = 100, message = "Warehouse description must be less than or equal to 100 characters")
    private String wareHouseDescription;

    @NotBlank(message = "Category is mandatory")
    @Size(max = 50, message = "Category must be less than or equal to 50 characters")
    private String category;
}
