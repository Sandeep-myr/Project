/**
 * @author 	SANJAYA SAHU
 * @eid 	10078
 * @version 2.0
 * @since	19-Aug-2024
 */
package com.rajutech.project.service;

import com.rajutech.project.dao.model.LoggedInUserDetails;
import com.rajutech.project.request.ProjGeneralValueRequest;

public interface ProjectSettingsService {
	
	Boolean saveProjGeneralValueDetails(ProjGeneralValueRequest projGeneralValueRequest, LoggedInUserDetails loggedInUserDetails);
	
}
