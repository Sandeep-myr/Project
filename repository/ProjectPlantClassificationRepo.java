package com.rajutech.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rajutech.project.dao.model.PlantMstrEntity;
import com.rajutech.project.dao.model.ProjPlantClassMstrEntity;

@Repository
public interface ProjectPlantClassificationRepo extends JpaRepository<ProjPlantClassMstrEntity, Long> {
	
	@Query("SELECT ppc FROM ProjPlantClassMstrEntity  ppc WHERE ppc.projId.projectId = :projectId AND ppc.status = :status")
	public  List<ProjPlantClassMstrEntity> getProjPlantClassifications(@Param("projectId") Long projectId,@Param("status") Integer status);

	@Query("SELECT pm FROM PlantMstrEntity pm WHERE pm.clientId.clientId = :crmId AND pm.status = :status")
	public List<PlantMstrEntity> getPlantClassifications(@Param("crmId") Long crmId,@Param("status") Integer active);



}
