package com.rajutech.project.service;

import java.util.List;
import java.util.Map;

import com.rajutech.project.dao.model.LoggedInUserDetails;
import com.rajutech.project.request.WarehouseStockyardRequest;
import com.rajutech.project.response.ProjWarehouseStockyardResponse;

public interface WarehouseStockyardService {

	public List<ProjWarehouseStockyardResponse> getProjWarehouseAndStockyardList(LoggedInUserDetails loggedInUserDetails,Integer status, Long projectId);

	public Map<String, Object> saveProjWarehouseAndStockyardList( WarehouseStockyardRequest warehouseStockyardRequest, LoggedInUserDetails loggedInUserDetails);

	
	public Boolean deactivateWarehouseAndStockyard(List<Long> id, Integer status);

}
