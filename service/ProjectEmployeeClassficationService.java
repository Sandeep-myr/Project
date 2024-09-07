package com.rajutech.project.service;

import java.util.List;
import java.util.Map;

import com.rajutech.project.dao.model.LoggedInUserDetails;
import com.rajutech.project.exception.POTException;
import com.rajutech.project.request.MaterialRestrictionRequest;
import com.rajutech.project.request.ProjectEmployeeClassRequest;
import com.rajutech.project.response.ProjectEmployeeClassificationResponse;

import jakarta.validation.Valid;

public interface ProjectEmployeeClassficationService {

	public List<ProjectEmployeeClassificationResponse> getAllProjEmpClassification(LoggedInUserDetails loggedInUserDetails,Long projectId) throws POTException;

	public Map<String, Object> saveAllProjectEmployeeClassifications(LoggedInUserDetails loggedInUserDetails,ProjectEmployeeClassRequest classificationRequests)throws POTException;


}
