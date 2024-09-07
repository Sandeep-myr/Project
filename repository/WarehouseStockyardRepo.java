package com.rajutech.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rajutech.project.dao.model.ProjStoreStockMstrEntity;

import jakarta.transaction.Transactional;


@Repository
@Transactional
public interface WarehouseStockyardRepo extends JpaRepository<ProjStoreStockMstrEntity, Long> {

	@Query("SELECT stm FROM ProjStoreStockMstrEntity stm WHERE stm.projectId.projectId = :projectId AND stm.status IN (:status)")
	public List<ProjStoreStockMstrEntity> getWareHouseData(@Param("projectId") Long projectId, @Param("status") List<Integer> status);

	
	@Modifying
	@Query("UPDATE ProjStoreStockMstrEntity p SET p.status = :status WHERE p.stockMstrId IN :ids")
	public Integer deactivateWarehouseAndStockyard(@Param("ids") List<Long> ids, @Param("status") Integer status);

}
