package com.rajutech.project.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.rajutech.project.common.Constants;
import com.rajutech.project.dao.model.EmpClassMstrEntity;
import com.rajutech.project.dao.model.LoggedInUserDetails;
import com.rajutech.project.dao.model.ProjEmpClassMstrEntity;
import com.rajutech.project.dao.model.ProjMstrEntity;
import com.rajutech.project.dao.model.UserMstrEntity;
import com.rajutech.project.exception.POTException;
import com.rajutech.project.repository.CustomQueryClass;
import com.rajutech.project.repository.ProjectEmployeeClassificationRepo;
import com.rajutech.project.request.ProjectEmployeeClassRequest;
import com.rajutech.project.request.ProjectEmployeeClassificationRequest;
import com.rajutech.project.response.ProjectEmployeeClassificationResponse;
import com.rajutech.project.service.ProjectEmployeeClassficationService;
import com.rajutech.project.util.AppUtil;

import jakarta.persistence.Table;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProjectEmployeeClassificationServiceImpl  implements ProjectEmployeeClassficationService{
	
	private CustomQueryClass customQueryClass;
	
	private ProjectEmployeeClassificationRepo employeeClassificationRepo;
	
	
	//================================================================================================================================
	
	// Fetching project employee classifications and employee classifications
	
	@Override
	public List<ProjectEmployeeClassificationResponse> getAllProjEmpClassification(LoggedInUserDetails loggedInUserDetails, Long projectId)throws POTException {
	    List<ProjEmpClassMstrEntity> projectEmpClassifications = employeeClassificationRepo.getProjEmpClassifications(projectId,  Constants.ACTIVE);
	    List<EmpClassMstrEntity> employeeClassifications = employeeClassificationRepo.getEmployeeClassifications(loggedInUserDetails.getCrmId(), Constants.ACTIVE);

	    Table tableNameAnnotation = ProjEmpClassMstrEntity.class.getAnnotation(Table.class);
	    String tableName = (tableNameAnnotation != null) ? tableNameAnnotation.name() : null;
	    List<Long> uniqueReferencedMeasurementIds = customQueryClass.fetchDistinctValues(tableName);
	    
	    List<ProjectEmployeeClassificationResponse> responseList = new ArrayList<>();
	    
	    if (AppUtil.isNotNull(employeeClassifications)) {
	        for (EmpClassMstrEntity empClassMstrEntity : employeeClassifications) {
	            ProjEmpClassMstrEntity matchingProjEmpClassMstrEntity = projectEmpClassifications.stream()
	                    .filter(projEmpClass -> empClassMstrEntity.getEmpClassMstrId().equals(projEmpClass.getEmpClassMstrEntity().getEmpClassMstrId()))
	                    .findFirst()
	                    .orElse(null);
	            
	            ProjectEmployeeClassificationResponse projectEmployeeClassificationResponse = new ProjectEmployeeClassificationResponse();

	            if (matchingProjEmpClassMstrEntity != null) {
	                projectEmployeeClassificationResponse.setProjEmpClassId(matchingProjEmpClassMstrEntity.getProjEmpClassId());
	                projectEmployeeClassificationResponse.setAsWorkerUnionName(matchingProjEmpClassMstrEntity.getUnionName());
	                projectEmployeeClassificationResponse.setEmployeeCategory(matchingProjEmpClassMstrEntity.getProjEmpCategory());
	                projectEmployeeClassificationResponse.setAsContractName(matchingProjEmpClassMstrEntity.getTradeContrName());
	                projectEmployeeClassificationResponse.setStatus(matchingProjEmpClassMstrEntity.getStatus());
	            } else {
	                projectEmployeeClassificationResponse.setProjEmpClassId(null);
	                projectEmployeeClassificationResponse.setAsWorkerUnionName(null);
	                projectEmployeeClassificationResponse.setEmployeeCategory(null);
	                projectEmployeeClassificationResponse.setAsContractName(null);
	                projectEmployeeClassificationResponse.setStatus(Constants.INACTIVE);
	            }

	            if(AppUtil.isNotNull(uniqueReferencedMeasurementIds)) {
	            	projectEmployeeClassificationResponse.setIsAssigned(uniqueReferencedMeasurementIds.contains(matchingProjEmpClassMstrEntity.getProjEmpClassId()));
	            }else{
	            	projectEmployeeClassificationResponse.setIsAssigned(false);
	            }
	            projectEmployeeClassificationResponse.setResourceCode(empClassMstrEntity.getCode());
	            projectEmployeeClassificationResponse.setResourceName(empClassMstrEntity.getName());
	            projectEmployeeClassificationResponse.setEmpClassificationId(empClassMstrEntity.getEmpClassMstrId());
	            projectEmployeeClassificationResponse.setUnitOfMeasureName(empClassMstrEntity.getMeasurmentMstrEntity().getName());
	            
	            responseList.add(projectEmployeeClassificationResponse);
	        }
	    }
       
	    return responseList;
	}



	//============================================================================================================================

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> saveAllProjectEmployeeClassifications(LoggedInUserDetails loggedInUserDetails, ProjectEmployeeClassRequest classificationRequests) throws POTException {
	    Map<String, Object> responseMap = new LinkedHashMap<>();
	    
	    List<ProjEmpClassMstrEntity> projectEmpClassifications = employeeClassificationRepo.getProjEmpClassifications(
	            classificationRequests.getProjectId(), Constants.ACTIVE);
	    
	    if (AppUtil.isNotNull(classificationRequests.getClassificationList())) {
	        List<ProjEmpClassMstrEntity> projEmpClassMstrEntities = new ArrayList<>();
	        UserMstrEntity userMstrEntity = new UserMstrEntity();
	        userMstrEntity.setUserId(loggedInUserDetails.getUserId());

	        for (ProjectEmployeeClassificationRequest request : classificationRequests.getClassificationList()) {
	            ProjEmpClassMstrEntity projEmpClassMstrEntity = projectEmpClassifications.stream()
	                .filter(p -> p.getProjEmpClassId() != null && p.getProjEmpClassId().equals(request.getProjEmpClassId()))
	                .findFirst()
	                .orElseGet(() -> createNewProjEmpClassEntity(userMstrEntity, classificationRequests.getProjectId(), request));

	            projEmpClassMstrEntity.setUpdatedBy(userMstrEntity);
	            projEmpClassMstrEntity.setUpdatedOn(new Date());

	            setCommonFields(projEmpClassMstrEntity, classificationRequests.getProjectId(), request);
	            projEmpClassMstrEntities.add(projEmpClassMstrEntity);
	        }

	        if (AppUtil.isNotNull(projEmpClassMstrEntities)) {
	            projEmpClassMstrEntities = employeeClassificationRepo.saveAll(projEmpClassMstrEntities);
	            if (AppUtil.isNotNull(projEmpClassMstrEntities)) {
	                responseMap.put("message", "Project Employee Classifications saved successfully");
	                responseMap.put("isSuccess", true);
	            } else {
	                responseMap.put("message", "Failed to save Project Employee Classifications");
	                responseMap.put("isSuccess", false);
	            }
	        }
	    }

	    return responseMap;
	}
	
	//-----------------------------------------------------------------------------------------------------

	private ProjEmpClassMstrEntity createNewProjEmpClassEntity(UserMstrEntity userMstrEntity, Long projectId, ProjectEmployeeClassificationRequest request) {
	    ProjEmpClassMstrEntity projEmpClassMstrEntity = new ProjEmpClassMstrEntity();
	    projEmpClassMstrEntity.setCreatedBy(userMstrEntity);
	    projEmpClassMstrEntity.setCreatedOn(new Date());
	    setCommonFields(projEmpClassMstrEntity, projectId, request);
	    return projEmpClassMstrEntity;
	}

	//-----------------------------------------------------------------------------------------------------
	
	private void setCommonFields(ProjEmpClassMstrEntity projEmpClassMstrEntity, Long projectId, ProjectEmployeeClassificationRequest request) {
	    EmpClassMstrEntity empClassMstrEntity = new EmpClassMstrEntity();
	    empClassMstrEntity.setEmpClassMstrId(request.getEmployeeClassificationId());
	    projEmpClassMstrEntity.setEmpClassMstrEntity(empClassMstrEntity);

	    ProjMstrEntity projMstrEntity = new ProjMstrEntity();
	    projMstrEntity.setProjectId(projectId);
	    projEmpClassMstrEntity.setProjectId(projMstrEntity);

	    projEmpClassMstrEntity.setProjEmpCategory(request.getEmployeeCategory());
	    projEmpClassMstrEntity.setStatus(request.getStatus());
	    projEmpClassMstrEntity.setTradeContrName(request.getAsContractName());
	    projEmpClassMstrEntity.setUnionName(request.getAsWorkerUnionName());
	}
	
  //===================================================================================================
	 
	
}
