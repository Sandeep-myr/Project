package com.rajutech.project.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.rajutech.project.common.Constants;
import com.rajutech.project.dao.model.LoggedInUserDetails;
import com.rajutech.project.dao.model.MaterialClassMstrEntity;
import com.rajutech.project.dao.model.ProjMaterialMstrEntity;
import com.rajutech.project.dao.model.UserMstrEntity;
import com.rajutech.project.repository.ProjectMaterialTransferRestrictionRepo;
import com.rajutech.project.request.MaterialRestrictionRequest;
import com.rajutech.project.request.ProjMaterialRestrictionRequest;
import com.rajutech.project.response.ProjectMaterialTransferRestrictionResponse;
import com.rajutech.project.service.ProjectMaterialTransferRestrictionService;
import com.rajutech.project.util.AppUtil;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProjectMaterialTransferRestrictionServiceImpl implements ProjectMaterialTransferRestrictionService{

	private ProjectMaterialTransferRestrictionRepo materialTransferRestrictionRepo; 
	
	//============================================================================================
	
	@Override
	public List<ProjectMaterialTransferRestrictionResponse> getAllProjMatTransferRestrictions(LoggedInUserDetails loggedInUserDetails, Long projectId) {
	    List<MaterialClassMstrEntity> materialClassMstrEntities = materialTransferRestrictionRepo.forPerticularClientMaterialData(loggedInUserDetails.getCrmId());
	    List<ProjMaterialMstrEntity> projMaterialEntities = materialTransferRestrictionRepo.getProjMtDataByProjectId(projectId);

	    Map<Long, ProjMaterialMstrEntity> projMaterialMap = projMaterialEntities.stream()
	            .collect(Collectors.toMap(e -> e.getGroupId().getId(), e -> e));

	    List<ProjectMaterialTransferRestrictionResponse> responseList = materialClassMstrEntities.stream()
	            .filter(entity -> entity.getMaterialClassMstrEntity() == null)
	            .map(entity -> buildMaterialClassResponse(entity, projMaterialMap))
	            .collect(Collectors.toList());

	    return responseList;
	}

	//--------------------------------------------------------------------------------------------------------------------
	
	private ProjectMaterialTransferRestrictionResponse buildMaterialClassResponse(MaterialClassMstrEntity materialClassMstrEntity, Map<Long, ProjMaterialMstrEntity> projMaterialMap) {
	    ProjectMaterialTransferRestrictionResponse response = new ProjectMaterialTransferRestrictionResponse();
	    response.setId(materialClassMstrEntity.getId());
	    response.setCode(materialClassMstrEntity.getCode());
	    response.setName(materialClassMstrEntity.getName());
	    response.setItem(materialClassMstrEntity.isItem());

	    if (materialClassMstrEntity.isItem()) {
	        ProjMaterialMstrEntity projMaterial = projMaterialMap.get(materialClassMstrEntity.getId());
	        if (projMaterial != null) {
	            response.setProjectMaterailClassId(projMaterial.getMaterialClassificationId());
	            response.setIsMaterialInternalTrx(projMaterial.getIntrlApproved());
	            response.setIsMaterialExternalTrx(projMaterial.getExtrlApproved());
	        } else {
	            response.setProjectMaterailClassId(null);
	            response.setIsMaterialInternalTrx(false);
	            response.setIsMaterialExternalTrx(false);
	        }
	        response.setUnitOfMeasureName(materialClassMstrEntity.getMeasurmentMstrEntity().getName());
	    } 

	    // Recursively build child responses
	    List<ProjectMaterialTransferRestrictionResponse> childResponses = materialClassMstrEntity.getChildEntities().stream()
	            .map(childEntity -> buildMaterialClassResponse(childEntity, projMaterialMap))
	            .collect(Collectors.toList());

	    response.setChildEntities(childResponses);

	    return response;
	}


	//================================================================================================

	@Override
	public Map<String, Object> saveAllProjectMaterialTransferRestrictions(LoggedInUserDetails loggedInUserDetails, MaterialRestrictionRequest transferRequests) {
	    // Retrieve existing project material data for the given project ID
	    List<ProjMaterialMstrEntity> projMaterialEntities = materialTransferRestrictionRepo.getProjMtDataByProjectId(transferRequests.getProjectId());
	    List<ProjMaterialMstrEntity> savedProjectMtClassificationList = new ArrayList<>();
	    Map<Long, ProjMaterialMstrEntity> projMaterialMap = projMaterialEntities.stream()
	            .collect(Collectors.toMap(ProjMaterialMstrEntity::getMaterialClassificationId, e -> e));
	    
	    UserMstrEntity userMstrEntity = new UserMstrEntity();
	    userMstrEntity.setUserId(loggedInUserDetails.getUserId());
	    
	    // Process each restriction request
	    for (ProjMaterialRestrictionRequest request : transferRequests.getProjMaterialRestrictionRequests()) {
	        processMaterialRequest(request, projMaterialMap, savedProjectMtClassificationList, userMstrEntity);
	        
	        // Iterate over child entities if they exist
	        if (request.getChildEntities() != null && !request.getChildEntities().isEmpty()) {
	            for (ProjMaterialRestrictionRequest childRequest : request.getChildEntities()) {
	                processMaterialRequest(childRequest, projMaterialMap, savedProjectMtClassificationList, userMstrEntity);
	            }
	        }
	    }

	    Map<String, Object> result = new HashMap<>();
	    
	    if (!savedProjectMtClassificationList.isEmpty()) {
	        savedProjectMtClassificationList = materialTransferRestrictionRepo.saveAll(savedProjectMtClassificationList);
	        if (savedProjectMtClassificationList != null) {
	            result.put("isSuccess", true);
	            result.put("message", "Material Transfer Restriction Data Successfully Added");
	        } else {
	            result.put("isSuccess", false);
	            result.put("message", "Failed to add material transfer restriction");
	        }
	    }
	    return result;
	}

	private void processMaterialRequest(ProjMaterialRestrictionRequest request, Map<Long, ProjMaterialMstrEntity> projMaterialMap,
	                                     List<ProjMaterialMstrEntity> savedProjectMtClassificationList, UserMstrEntity userMstrEntity) {
	    ProjMaterialMstrEntity existingEntity = projMaterialMap.get(request.getMaterialClassid());
	    if (request.getIsItem()) {
	        if (existingEntity != null) {
	            // Update existing record
	            existingEntity.setIntrlApproved(request.getIsMaterialInternalTrx() != null ? request.getIsMaterialInternalTrx() : false);
	            existingEntity.setExtrlApproved(request.getIsMaterialExternalTrx() != null ? request.getIsMaterialExternalTrx() : false);
	            existingEntity.setUpdatedBy(userMstrEntity);
	            existingEntity.setUpdatedOn(new Date());
	            // Save updated record
	            savedProjectMtClassificationList.add(existingEntity);
	        } else {
	            // Create new record
	            ProjMaterialMstrEntity newEntity = new ProjMaterialMstrEntity();
	            MaterialClassMstrEntity materialClassMstrEntity = new MaterialClassMstrEntity();
	            materialClassMstrEntity.setId(request.getMaterialClassid());
	            newEntity.setGroupId(materialClassMstrEntity);
	            newEntity.setMaterialClassificationId(request.getProjectMaterailClassId());
	            newEntity.setIntrlApproved(request.getIsMaterialInternalTrx() != null ? request.getIsMaterialInternalTrx() : false);
	            newEntity.setExtrlApproved(request.getIsMaterialExternalTrx() != null ? request.getIsMaterialExternalTrx() : false);
	            newEntity.setStatus(Constants.ACTIVE);
	            newEntity.setCreatedBy(userMstrEntity);
	            newEntity.setCreatedOn(new Date());
	            
	            // Save new record
	            savedProjectMtClassificationList.add(newEntity);
	        }
	    }
	}


}
