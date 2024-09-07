package com.rajutech.project.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.rajutech.project.common.Constants;
import com.rajutech.project.dao.model.LoggedInUserDetails;
import com.rajutech.project.dao.model.ProjMstrEntity;
import com.rajutech.project.dao.model.ProjStoreStockMstrEntity;
import com.rajutech.project.dao.model.UserMstrEntity;
import com.rajutech.project.repository.CustomQueryClass;
import com.rajutech.project.repository.WarehouseStockyardRepo;
import com.rajutech.project.request.WarehouseStockyardListRequest;
import com.rajutech.project.request.WarehouseStockyardRequest;
import com.rajutech.project.response.ProjWarehouseStockyardResponse;
import com.rajutech.project.service.WarehouseStockyardService;
import com.rajutech.project.util.AppUtil;

import jakarta.persistence.Table;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class WarehouseStockyardServiceImpl implements WarehouseStockyardService {
	
	private WarehouseStockyardRepo warehouseStockyardRepo;
    
	private CustomQueryClass customQueryClass;

	//================================================================================================
	
	@Override
	public List<ProjWarehouseStockyardResponse> getProjWarehouseAndStockyardList(LoggedInUserDetails loggedInUserDetails, Integer status, Long projectId) {
	    
		List<ProjStoreStockMstrEntity> projStoreStockMstrEntities = warehouseStockyardRepo.getWareHouseData(projectId, new ArrayList<Integer>(List.of(status)));
	    if (!AppUtil.isNotNull( projStoreStockMstrEntities)) {
	        return new ArrayList<>(); 
	    }
	    Table tableNameAnnotation = ProjStoreStockMstrEntity.class.getAnnotation(Table.class);
	    String tableName = (tableNameAnnotation != null) ? tableNameAnnotation.name() : null;
	    List<Long> uniqueReferencedMeasurementIds = (tableName != null) ? customQueryClass.fetchDistinctValues(tableName): new ArrayList<>();
	    List<ProjWarehouseStockyardResponse> projWarehouseStockyardResponses = projStoreStockMstrEntities.stream()
	        .map((ProjStoreStockMstrEntity projStoreStockMstrEntity) -> {
	            boolean isAssigned = AppUtil.isNotNull(uniqueReferencedMeasurementIds) && uniqueReferencedMeasurementIds.contains(projStoreStockMstrEntity.getStockMstrId());
	            return ProjWarehouseStockyardResponse.builder()
	                .status(status)
	                .storeLocationCode(projStoreStockMstrEntity.getStockCode())
	                .storeLocationName(projStoreStockMstrEntity.getDesc())
	                .wareHouseId(projStoreStockMstrEntity.getStockMstrId())
	                .isAssigned(isAssigned)
	                .category(projStoreStockMstrEntity.getCategory())
	                .build();
	        })
	        .collect(Collectors.toList());

	    return projWarehouseStockyardResponses;
	}

	
	//============================================================================================

	@Override
	public Map<String, Object> saveProjWarehouseAndStockyardList(WarehouseStockyardRequest warehouseStockyardRequests, LoggedInUserDetails loggedInUserDetails) {
	    Map<String, Object> responseMap = new HashMap<>();
	    List<String> duplicateCodes = new ArrayList<>();
	    List<ProjStoreStockMstrEntity> entitiesToSave = new ArrayList<>();
	    List<Integer> statusList = Arrays.asList(Constants.ACTIVE, Constants.INACTIVE);
	    
	    // Fetch existing warehouse/stockyard data for validation
	    List<ProjStoreStockMstrEntity> existingWarehouses = warehouseStockyardRepo.getWareHouseData(warehouseStockyardRequests.getProjectId(), statusList);
	    
	    // Map to hold existing warehouses for quick lookup
	    Map<Long, ProjStoreStockMstrEntity> existingWarehouseMap = existingWarehouses.stream()
	        .collect(Collectors.toMap(ProjStoreStockMstrEntity::getStockMstrId, warehouse -> warehouse));

	    for (WarehouseStockyardListRequest request : warehouseStockyardRequests.getWarehouseListRequest()) {
	        if (request == null) continue;

	        // Case 1: New warehouse/stockyard (no ID provided)
	        if (request.getWareHouseId() == null) {
	            boolean isDuplicate = existingWarehouses.stream()
	                .anyMatch(warehouse -> warehouse.getStockCode().equals(request.getWareHouseCode()));
	            
	            if (isDuplicate) {
	                duplicateCodes.add(request.getWareHouseCode());
	            } else {
	                // Create new entity
	                ProjStoreStockMstrEntity newEntity = createWarehouseEntity(request, loggedInUserDetails,warehouseStockyardRequests.getProjectId());
	                entitiesToSave.add(newEntity);
	            }
	        } else {
	            // Case 2: Update existing warehouse/stockyard (ID provided)
	            ProjStoreStockMstrEntity existingEntity = existingWarehouseMap.get(request.getWareHouseId());
	            if (existingEntity != null) {
	                updateWarehouseEntity(existingEntity, request, loggedInUserDetails);
	                entitiesToSave.add(existingEntity);
	            }
	        }
	    }

	    // Save all valid entities
	    if (!entitiesToSave.isEmpty()) {
	        warehouseStockyardRepo.saveAll(entitiesToSave);
	        responseMap.put("isSuccess", true);
	        responseMap.put("message", "Warehouse/Stockyard data saved/updated successfully.");
	    } else {
	        responseMap.put("isSuccess", false);
	        if (!duplicateCodes.isEmpty()) {
	            responseMap.put("message", "Duplicate Warehouse/Stockyard Code(s): " + String.join(", ", duplicateCodes));
	        } else {
	            responseMap.put("message", "No Warehouse/Stockyard data to save.");
	        }
	    }

	    return responseMap;
	}

	//------------------------------------------------------------------------------------------------
	
	// Helper method to create a new warehouse entity
	private ProjStoreStockMstrEntity createWarehouseEntity(WarehouseStockyardListRequest request, LoggedInUserDetails loggedInUserDetails ,Long projectId) {
	    return ProjStoreStockMstrEntity.builder()
	        .stockCode(request.getWareHouseCode())
	        .desc(request.getWareHouseDescription())
	        .status(Constants.ACTIVE)
	        .projectId(ProjMstrEntity.builder().projectId(projectId).build())
	        .category(request.getCategory())
	        .createdBy(UserMstrEntity.builder().userId(loggedInUserDetails.getUserId()).build())
	        .createdOn(new Date())
	        .build();
	}

	//------------------------------------------------------------------------------
	
	// Helper method to update an existing warehouse entity
	private void updateWarehouseEntity(ProjStoreStockMstrEntity existingEntity, WarehouseStockyardListRequest request, LoggedInUserDetails loggedInUserDetails) {
	    existingEntity.setStockCode(request.getWareHouseCode());
	    existingEntity.setDesc(request.getWareHouseDescription());
	    existingEntity.setCategory(request.getCategory());
	    existingEntity.setUpdatedBy(UserMstrEntity.builder().userId(loggedInUserDetails.getUserId()).build());
	    existingEntity.setUpdatedOn(new Date());
	}

	//============================================================================================================

	@Override
	public Boolean deactivateWarehouseAndStockyard(List<Long> ids, Integer status) {
	    Boolean isUpdated = false;
	    Integer result = warehouseStockyardRepo.deactivateWarehouseAndStockyard(ids, status);
	    if (result >= 1) {
	        isUpdated = true;
	    }
	    return isUpdated;
	}



}
