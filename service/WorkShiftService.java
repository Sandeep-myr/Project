package com.rajutech.project.service;

import java.util.List;
import java.util.Map;

import com.rajutech.project.dao.model.LoggedInUserDetails;
import com.rajutech.project.request.WorkShiftRequest;
import com.rajutech.project.response.ProjWorkShiftResponse;

public interface WorkShiftService {

	public List<ProjWorkShiftResponse> getProjWorkShiftList(LoggedInUserDetails loggedInUserDetails, Integer status,Long projectId);

	public Map<String, Object> saveProjWorkShiftList( WorkShiftRequest workShiftRequest,LoggedInUserDetails loggedInUserDetails);

	public Boolean deactivateProjWorkShiftList(List<Long> ids, Integer status);

}
