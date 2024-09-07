package com.rajutech.project.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.rajutech.project.common.Constants;
import com.rajutech.project.dao.model.LoggedInUserDetails;
import com.rajutech.project.dao.model.PlantMstrEntity;
import com.rajutech.project.dao.model.ProjMstrEntity;
import com.rajutech.project.dao.model.ProjPlantClassMstrEntity;
import com.rajutech.project.dao.model.UserMstrEntity;
import com.rajutech.project.exception.POTException;
import com.rajutech.project.repository.CustomQueryClass;
import com.rajutech.project.repository.ProjectPlantClassificationRepo;
import com.rajutech.project.request.ProjectPlantClassRequest;
import com.rajutech.project.request.ProjectPlantClassificationRequest;
import com.rajutech.project.response.ProjectPlantClassificationResponse;
import com.rajutech.project.service.ProjectPlantClassficationService;
import com.rajutech.project.util.AppUtil;

import jakarta.persistence.Table;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class projectPlantClassficationServiceImpl implements ProjectPlantClassficationService {

	private CustomQueryClass customQueryClass;

	private ProjectPlantClassificationRepo projectPlantClassificationRepo;
	
	
	@Override
	public List<ProjectPlantClassificationResponse> getAllProjPlantClassification(LoggedInUserDetails loggedInUserDetails, Long projectId) throws POTException {
	    List<ProjPlantClassMstrEntity> projectPlantClassifications = projectPlantClassificationRepo.getProjPlantClassifications(projectId,  Constants.ACTIVE);
	    List<PlantMstrEntity> plantClassifications = projectPlantClassificationRepo.getPlantClassifications(loggedInUserDetails.getCrmId(), Constants.ACTIVE);

	    Table tableNameAnnotation = ProjPlantClassMstrEntity.class.getAnnotation(Table.class);
	    String tableName = (tableNameAnnotation != null) ? tableNameAnnotation.name() : null;
	    List<Long> uniqueReferencedMeasurementIds = customQueryClass.fetchDistinctValues(tableName);
	    
	    List<ProjectPlantClassificationResponse> responseList = new ArrayList<>();
	    
	    if (AppUtil.isNotNull(plantClassifications)) {
	        for (PlantMstrEntity plantClassMstrEntity : plantClassifications) {
	            ProjPlantClassMstrEntity matchingProjPlantClassMstrEntity = projectPlantClassifications.stream()
	                    .filter(projPlantClass -> plantClassMstrEntity.getPlantClassifictaionId().equals(projPlantClass.getPlantMstrEntity().getPlantClassifictaionId()))
	                    .findFirst()
	                    .orElse(null);
	            
	            ProjectPlantClassificationResponse projectEmployeeClassificationResponse = new ProjectPlantClassificationResponse();

	            if (matchingProjPlantClassMstrEntity != null) {
	                projectEmployeeClassificationResponse.setProjPlantClassId(matchingProjPlantClassMstrEntity.getPlantClassId());
	                projectEmployeeClassificationResponse.setAsContractName(matchingProjPlantClassMstrEntity.getPlantContrName());
	                projectEmployeeClassificationResponse.setStatus(matchingProjPlantClassMstrEntity.getStatus());
	            } else {
	                projectEmployeeClassificationResponse.setProjPlantClassId(null);
	                projectEmployeeClassificationResponse.setAsContractName(null);
	                projectEmployeeClassificationResponse.setStatus(Constants.INACTIVE);
	            }
	            if(AppUtil.isNotNull(uniqueReferencedMeasurementIds)) {
	            	projectEmployeeClassificationResponse.setIsAssigned(uniqueReferencedMeasurementIds.contains(matchingProjPlantClassMstrEntity.getPlantClassId()));
	            }else{
	            	projectEmployeeClassificationResponse.setIsAssigned(false);
	            }
	            projectEmployeeClassificationResponse.setResourceCode(plantClassMstrEntity.getCode());
	            projectEmployeeClassificationResponse.setResourceName(plantClassMstrEntity.getName());
	            projectEmployeeClassificationResponse.setPlantClassificationId(plantClassMstrEntity.getPlantClassifictaionId());
	            projectEmployeeClassificationResponse.setUnitOfMeasureName(plantClassMstrEntity.getMeasurmentMstrEntity().getName());
	            
	            responseList.add(projectEmployeeClassificationResponse);
	        }
	    }
       
	    return responseList;
	}


	//======================================================================
	
	@Override
	public Map<String, Object> saveAllProjectPlantClassifications(LoggedInUserDetails loggedInUserDetails, ProjectPlantClassRequest classificationRequests) throws POTException {
	    Map<String, Object> responseMap = new LinkedHashMap<>();
	    
	    // Fetch the existing plant classifications for the project
	    List<ProjPlantClassMstrEntity> projectPlantClassifications = projectPlantClassificationRepo
	            .getProjPlantClassifications(classificationRequests.getProjectId(), Constants.ACTIVE);
	    
	    if (AppUtil.isNotNull(classificationRequests.getClassificationList())) {
	        List<ProjPlantClassMstrEntity> projPlantClassMstrEntities = new ArrayList<>();
	        UserMstrEntity userMstrEntity = new UserMstrEntity();
	        userMstrEntity.setUserId(loggedInUserDetails.getUserId());

	        // Process each classification request
	        for (ProjectPlantClassificationRequest request : classificationRequests.getClassificationList()) {
	            ProjPlantClassMstrEntity projPlantClassMstrEntity = projectPlantClassifications.stream()
	                .filter(p -> p.getPlantClassId() != null && p.getPlantClassId().equals(request.getProjPlantClassId()))
	                .findFirst()
	                .orElseGet(() -> createNewProjPlantClassEntity(userMstrEntity, classificationRequests.getProjectId(), request));

	            // Set common fields and update details
	            if(projPlantClassMstrEntity.getPlantClassId()!=null) {
	            projPlantClassMstrEntity.setUpdatedBy(userMstrEntity);
	            projPlantClassMstrEntity.setUpdatedOn(new Date());
	            }
	            setCommonFields(projPlantClassMstrEntity, classificationRequests.getProjectId(), request);
	            projPlantClassMstrEntities.add(projPlantClassMstrEntity);
	        }

	        // Save all plant classifications
	        if (AppUtil.isNotNull(projPlantClassMstrEntities)) {
	            projPlantClassMstrEntities = projectPlantClassificationRepo.saveAll(projPlantClassMstrEntities);
	            if (AppUtil.isNotNull(projPlantClassMstrEntities)) {
	                responseMap.put("message", "Project Plant Classifications saved successfully.");
	                responseMap.put("isSuccess", true);
	            } else {
	                responseMap.put("message", "Failed to save Project Plant Classifications.");
	                responseMap.put("isSuccess", false);
	            }
	        }
	    } else {
	        responseMap.put("message", "No valid classifications to save.");
	        responseMap.put("isSuccess", false);
	    }

	    return responseMap;
	}

	//================================================================================================================
	
	private void setCommonFields(ProjPlantClassMstrEntity projPlantClassMstrEntity, Long projectId,ProjectPlantClassificationRequest request) {
		PlantMstrEntity plantMstrEntity = new PlantMstrEntity();
		plantMstrEntity.setPlantClassifictaionId(request.getPlantClassificationId());
		projPlantClassMstrEntity.setPlantMstrEntity(plantMstrEntity);

		ProjMstrEntity projMstrEntity = new ProjMstrEntity();
		projMstrEntity.setProjectId(projectId);
		projPlantClassMstrEntity.setProjId(projMstrEntity);

		projPlantClassMstrEntity.setPlantContrName(request.getAsContractName());
		projPlantClassMstrEntity.setStatus(request.getStatus());
	}
	//-----------------------------------------------------------------------
	
	private ProjPlantClassMstrEntity createNewProjPlantClassEntity(UserMstrEntity userMstrEntity, Long projectId, ProjectPlantClassificationRequest request) {
		ProjPlantClassMstrEntity projPlantClassMstrEntity = new ProjPlantClassMstrEntity();
		projPlantClassMstrEntity.setCreatedBy(userMstrEntity);
		projPlantClassMstrEntity.setCreatedOn(new Date());
		setCommonFields(projPlantClassMstrEntity, projectId, request);
		return projPlantClassMstrEntity;
	}




}
