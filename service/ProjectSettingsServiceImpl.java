/**
 * @author 	SANJAYA SAHU
 * @eid 	10078
 * @version 2.0
 * @since	04-Sep-2024
 */
package com.rajutech.project.service;

import java.time.LocalDateTime;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.rajutech.project.common.Constants;
import com.rajutech.project.dao.model.CompanyMstrEntity;
import com.rajutech.project.dao.model.FinanceCenterEntity;
import com.rajutech.project.dao.model.GlobalCalEntity;
import com.rajutech.project.dao.model.LoggedInUserDetails;
import com.rajutech.project.dao.model.ProfitCentreEntity;
import com.rajutech.project.dao.model.ProjGeneralMstrEntity;
import com.rajutech.project.dao.model.ProjMstrEntity;
import com.rajutech.project.dao.model.ResourceCurveEntity;
import com.rajutech.project.dao.model.UserMstrEntity;
import com.rajutech.project.repository.ProjGeneralValuesRepository;
import com.rajutech.project.request.ProjGeneralValueRequest;
import com.rajutech.project.util.AppUtil;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProjectSettingsServiceImpl implements ProjectSettingsService {

	private ProjGeneralValuesRepository projGeneralValuesRepository;

	@Override
	public Boolean saveProjGeneralValueDetails(ProjGeneralValueRequest projGeneralValueRequest, LoggedInUserDetails loggedInUserDetails) {
		Boolean isSaved = false;
		LocalDateTime localDateTime = LocalDateTime.now();

		ProjGeneralMstrEntity projGeneralMstrEntity = new ProjGeneralMstrEntity();
		BeanUtils.copyProperties(projGeneralValueRequest, projGeneralMstrEntity);
		projGeneralMstrEntity.setCompanyMstrEntity(CompanyMstrEntity.builder().companyMstrId(projGeneralValueRequest.getCompanyMstrId()).build());
		projGeneralMstrEntity.setMainContractCompany(CompanyMstrEntity.builder().companyMstrId(projGeneralValueRequest.getMainContractCompany()).build());
		projGeneralMstrEntity.setProjMstrEntity(ProjMstrEntity.builder().projectId(projGeneralValueRequest.getProjectId()).build());
		projGeneralMstrEntity.setRespManager(UserMstrEntity.builder().userId(projGeneralValueRequest.getRespManager()).build());
		projGeneralMstrEntity.setProfitCentreEntity(ProfitCentreEntity.builder().profitCentreId(projGeneralValueRequest.getProfitCentreId()).build());
		projGeneralMstrEntity.setProjResourceCurveEntity(ResourceCurveEntity.builder().id(projGeneralValueRequest.getProjResourceCurveId()).build());
		projGeneralMstrEntity.setFinanceCentre(FinanceCenterEntity.builder().financeCenterId(projGeneralValueRequest.getFinanceCentreId()).build());
		projGeneralMstrEntity.setGlobalCalEntity(GlobalCalEntity.builder().globalCalId(projGeneralValueRequest.getGlobalCalId()).build());
		projGeneralMstrEntity.setStatus(Constants.ACTIVE);

		if (AppUtil.isNotNull(projGeneralValueRequest.getPgvId())) {
			projGeneralMstrEntity.setUpdatedBy(UserMstrEntity.builder().userId(loggedInUserDetails.getUserId()).build());
			projGeneralMstrEntity.setUpdatedOn(localDateTime);
		} else {
			projGeneralMstrEntity.setCreatedBy(UserMstrEntity.builder().userId(loggedInUserDetails.getUserId()).build());
			projGeneralMstrEntity.setCreatedOn(localDateTime);
		}

		ProjGeneralMstrEntity savedProjGeneralMstrEntity = projGeneralValuesRepository.save(projGeneralMstrEntity);
		
		if (AppUtil.isNotNull(savedProjGeneralMstrEntity.getPgvId()))
			isSaved = true;
		return isSaved;
	}
}
