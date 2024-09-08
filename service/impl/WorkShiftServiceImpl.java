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
import com.rajutech.project.dao.model.ProjWorkShiftMstrEntity;
import com.rajutech.project.dao.model.UserMstrEntity;
import com.rajutech.project.repository.CustomQueryClass;
import com.rajutech.project.repository.WorkShiftRepo;
import com.rajutech.project.request.WorkShiftRequest;
import com.rajutech.project.request.WorkShiftRequestList;
import com.rajutech.project.response.ProjWorkShiftResponse;
import com.rajutech.project.service.WorkShiftService;
import com.rajutech.project.util.AppUtil;

import jakarta.persistence.Table;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class WorkShiftServiceImpl implements WorkShiftService {
	
	private WorkShiftRepo workShiftRepo;
	
	private CustomQueryClass customQueryClass;

	//============================================================================

	@Override
	public List<ProjWorkShiftResponse> getProjWorkShiftList(LoggedInUserDetails loggedInUserDetails, Integer status, Long projectId) {
		
		List<Integer> statusList = new ArrayList<Integer>(List.of(status));
		List<ProjWorkShiftMstrEntity> workShiftEntities = workShiftRepo.getWorkShiftData(projectId, statusList);

		if (!AppUtil.isNotNull(workShiftEntities)) {
			return new ArrayList<>();
		}

		Table tableNameAnnotation = ProjWorkShiftMstrEntity.class.getAnnotation(Table.class);
	    String tableName = (tableNameAnnotation != null) ? tableNameAnnotation.name() : null;
		List<Long> uniqueAssignedShiftIds = customQueryClass.fetchDistinctValues(tableName);

		return workShiftEntities.stream()
				.map(workShiftEntity -> {
					boolean isAssigned = uniqueAssignedShiftIds.contains(workShiftEntity.getWorkShiftId());
					return ProjWorkShiftResponse.builder()
							.workShiftId(workShiftEntity.getWorkShiftId())
							.shiftName(workShiftEntity.getCode())
							.startHour(workShiftEntity.getStartHours())
							.duration(workShiftEntity.getFinishHours() - workShiftEntity.getStartHours())  // Example duration logic
							.status(workShiftEntity.getStatus())
							.isAssigned(isAssigned)
							.build();
				})
				.collect(Collectors.toList());
	}

	//============================================================================

	@Override
	public Map<String, Object> saveProjWorkShiftList(@Valid WorkShiftRequest workShiftRequest, LoggedInUserDetails loggedInUserDetails) {
		Map<String, Object> responseMap = new HashMap<>();
		List<String> duplicateShiftCodes = new ArrayList<>();
		List<ProjWorkShiftMstrEntity> entitiesToSave = new ArrayList<>();
        List<Integer> statusList = Arrays.asList(Constants.ACTIVE,Constants.INACTIVE);
		List<ProjWorkShiftMstrEntity> existingShifts = workShiftRepo.getWorkShiftData(workShiftRequest.getProjectId(), statusList);

		Map<Long, ProjWorkShiftMstrEntity> existingShiftMap = existingShifts.stream()
				.collect(Collectors.toMap(ProjWorkShiftMstrEntity::getWorkShiftId, shift -> shift));

		for (WorkShiftRequestList request : workShiftRequest.getWorkShiftRequests()) {
			if (request == null) continue;

			if (request.getWorkShiftId() == null) {
				boolean isDuplicate = existingShifts.stream()
						.anyMatch(shift -> shift.getCode().equals(request.getWorkShiftName()));

				if (isDuplicate) {
					duplicateShiftCodes.add(request.getWorkShiftName());
				} else {
					ProjWorkShiftMstrEntity newEntity = createWorkShiftEntity(request, loggedInUserDetails, workShiftRequest.getProjectId());
					entitiesToSave.add(newEntity);
				}
			} else {
				ProjWorkShiftMstrEntity existingEntity = existingShiftMap.get(request.getWorkShiftId());
				if (existingEntity != null) {
					updateWorkShiftEntity(existingEntity, request, loggedInUserDetails);
					entitiesToSave.add(existingEntity);
				}
			}
		}

		if (!entitiesToSave.isEmpty()) {
			workShiftRepo.saveAll(entitiesToSave);
			responseMap.put("isSuccess", true);
			responseMap.put("message", "Work shift data saved/updated successfully.");
		} else {
			responseMap.put("isSuccess", false);
			if (!duplicateShiftCodes.isEmpty()) {
				responseMap.put("message", "Duplicate Work Shift Code(s): " + String.join(", ", duplicateShiftCodes));
			} else {
				responseMap.put("message", "No Work Shift data to save.");
			}
		}

		return responseMap;
	}

	//============================================================================

	@Override
	public Boolean deactivateProjWorkShiftList(List<Long> ids, Integer status) {
		Boolean isUpdated = false;
		Integer result = workShiftRepo.deactivateWorkShift(ids, status);
		if (result >= 1) {
			isUpdated = true;
		}
		return isUpdated;
	}

	//============================================================================

	private ProjWorkShiftMstrEntity createWorkShiftEntity(WorkShiftRequestList request, LoggedInUserDetails loggedInUserDetails, Long projectId) {
		UserMstrEntity userMstrEntity = new UserMstrEntity();
		userMstrEntity.setUserId(loggedInUserDetails.getUserId());
		return ProjWorkShiftMstrEntity.builder()
				.code(request.getWorkShiftName())
				.startHours(request.getStartTime())
				.finishHours(request.getEndTime())
				.status(Constants.ACTIVE)  // Assuming '1' is ACTIVE
				.projectId(projectId)
				.createdBy(userMstrEntity)
				.createdOn(new Date())
				.build();
	}

	//============================================================================

	private void updateWorkShiftEntity(ProjWorkShiftMstrEntity existingEntity, WorkShiftRequestList request, LoggedInUserDetails loggedInUserDetails) {
		UserMstrEntity userMstrEntity = new UserMstrEntity();
		userMstrEntity.setUserId(loggedInUserDetails.getUserId());
		existingEntity.setCode(request.getWorkShiftName());
		existingEntity.setStartHours(request.getStartTime());
		existingEntity.setFinishHours(request.getEndTime());
		existingEntity.setUpdatedBy(userMstrEntity);
		existingEntity.setUpdatedOn(new Date());
	}
}
