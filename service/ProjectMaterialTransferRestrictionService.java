package com.rajutech.project.service;

import java.util.List;
import java.util.Map;

import com.rajutech.project.dao.model.LoggedInUserDetails;
import com.rajutech.project.request.MaterialRestrictionRequest;
import com.rajutech.project.response.ProjectMaterialTransferRestrictionResponse;

public interface ProjectMaterialTransferRestrictionService {

	public List<ProjectMaterialTransferRestrictionResponse> getAllProjMatTransferRestrictions(LoggedInUserDetails loggedInUserDetails, Long projectId);

	public Map<String, Object> saveAllProjectMaterialTransferRestrictions(LoggedInUserDetails loggedInUserDetails,MaterialRestrictionRequest transferRequests);

}
