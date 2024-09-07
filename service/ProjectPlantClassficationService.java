package com.rajutech.project.service;

import java.util.List;
import java.util.Map;

import com.rajutech.project.dao.model.LoggedInUserDetails;
import com.rajutech.project.exception.POTException;
import com.rajutech.project.request.ProjectPlantClassRequest;
import com.rajutech.project.response.ProjectPlantClassificationResponse;

public interface ProjectPlantClassficationService {

	public List<ProjectPlantClassificationResponse> getAllProjPlantClassification(LoggedInUserDetails loggedInUserDetails,Long projectId) throws POTException;

	public Map<String, Object> saveAllProjectPlantClassifications(LoggedInUserDetails loggedInUserDetails,ProjectPlantClassRequest classificationRequests) throws POTException;

}
