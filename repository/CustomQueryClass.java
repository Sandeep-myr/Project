package com.rajutech.project.repository;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

@Repository
public class CustomQueryClass {

	
	 @PersistenceContext
	 private EntityManager entityManager;
	
	 @SuppressWarnings("unchecked")
	 @Transactional
	 public List<Long> fetchDistinctValues(String tableName) {
	     List<Long> results = null;
	     entityManager.createNativeQuery("SET SESSION group_concat_max_len = 1000000").executeUpdate();
	     String sql = "SELECT GROUP_CONCAT(CONCAT('SELECT DISTINCT ', kcu.COLUMN_NAME, ' AS value FROM ', kcu.TABLE_NAME) SEPARATOR ' UNION ALL ') AS Query " +
	                  "FROM INFORMATION_SCHEMA.KEY_COLUMN_USAGE kcu " +
	                  "WHERE kcu.REFERENCED_TABLE_NAME = :tableName";
	     Query query = entityManager.createNativeQuery(sql);
	     query.setParameter("tableName", tableName);
	     String generatedQuery = (String) query.getSingleResult();
	     if (generatedQuery != null && !generatedQuery.isEmpty()) {
	         Query finalQuery = entityManager.createNativeQuery(generatedQuery);
	         results = (List<Long>) finalQuery.getResultList();
	         results = results.stream()
	                          .filter(Objects::nonNull)
	                          .collect(Collectors.toCollection(ArrayList::new));
	         Set<Long> updated = new HashSet<>(results);
	         results = new ArrayList<>(updated);
	     }
	     return results;
	 }

	
}
