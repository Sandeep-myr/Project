/**
 * @author 	SANJAYA SAHU
 * @eid 	10078
 * @version 2.0
 * @since	04-Sep-2024
 */
package com.rajutech.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rajutech.project.dao.model.ProjGeneralMstrEntity;

public interface ProjGeneralValuesRepository extends JpaRepository<ProjGeneralMstrEntity, Long> {

}
