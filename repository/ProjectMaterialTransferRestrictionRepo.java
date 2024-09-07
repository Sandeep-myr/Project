package com.rajutech.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rajutech.project.dao.model.MaterialClassMstrEntity;
import com.rajutech.project.dao.model.ProjMaterialMstrEntity;

@Repository
public interface ProjectMaterialTransferRestrictionRepo extends JpaRepository<ProjMaterialMstrEntity, Long> {

	@Query("SELECT pmc FROM ProjMaterialMstrEntity pmc WHERE pmc.projectId.projectId = :projectId AND pmc.status = 1")
	public List<ProjMaterialMstrEntity> getProjMtDataByProjectId(@Param("projectId") Long projectId);

	@Query("SELECT mc FROM MaterialClassMstrEntity mc WHERE mc.clientId.clientId = :crmId AND mc.status = 1")
	public List<MaterialClassMstrEntity> forPerticularClientMaterialData(@Param("crmId") Long crmId);

	
}
