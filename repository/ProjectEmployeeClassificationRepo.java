package com.rajutech.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rajutech.project.dao.model.EmpClassMstrEntity;
import com.rajutech.project.dao.model.ProjEmpClassMstrEntity;

@Repository
public interface ProjectEmployeeClassificationRepo  extends JpaRepository<ProjEmpClassMstrEntity, Long>{

	@Query("SELECT pec FROM ProjEmpClassMstrEntity  pec WHERE pec.projectId.projectId = :projectId AND pec.status = :status")
	public  List<ProjEmpClassMstrEntity> getProjEmpClassifications(@Param("projectId") Long projectId,@Param("status") Integer status);

	@Query("SELECT emp FROM EmpClassMstrEntity emp WHERE emp.clientId.clientId = :crmId AND emp.status = :status")
	public List<EmpClassMstrEntity> getEmployeeClassifications(@Param("crmId") Long crmId,@Param("status") Integer active);

}
