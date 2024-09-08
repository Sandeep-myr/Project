package com.rajutech.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rajutech.project.dao.model.ProjWorkShiftMstrEntity;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface WorkShiftRepo extends JpaRepository<ProjWorkShiftMstrEntity, Long> {

	@Query("SELECT psm FROM ProjWorkShiftMstrEntity psm WHERE psm.projectId = :projectId AND psm.status In :status")
	public List<ProjWorkShiftMstrEntity> getWorkShiftData(@Param("projectId")Long projectId,@Param("status") List<Integer> status);

	@Modifying
    @Query("UPDATE ProjWorkShiftMstrEntity p SET p.status = :status WHERE p.workShiftId IN :ids")
    public Integer deactivateWorkShift(@Param("ids") List<Long> ids, @Param("status") Integer status);

}
