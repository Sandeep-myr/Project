package com.rajutech.project.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rajutech.project.dao.model.UserMstrEntity;


public interface UserMasterRepository extends JpaRepository<UserMstrEntity, Long> {

	@Query(value = "SELECT * FROM user_mstr WHERE USR_ID!=?1 AND USR_CRM_ID=?2  AND usr_status=?3 AND is_client=?4", nativeQuery = true)
	List<UserMstrEntity> getApproversList(@Param("userId") String userId, @Param("crmId") String crmId, @Param("userStatus") String userStatus, @Param("isClient") String isClient);

	@Query("SELECT d FROM UserMstrEntity d WHERE d.userId IN (:userIds)")
	List<UserMstrEntity> getUsersDetails(@Param("userIds") Set<Long> userIds);

	@Query("SELECT d.userId, d.firstName, d.lastName FROM UserMstrEntity d WHERE d.userId IN (:userIds)")
	List<Object[]> getUsersNames(@Param("userIds") Set<Long> userIds);
	
//	@Query("SELECT um.clientRegEntity, um.empDesg FROM UserMstrEntity um JOIN User u ON u.username = um.userName WHERE u.username = :username")
//	Optional<List<ClientRegEntity>> getAllCRMsbyUsername(@Param("username") String username);

	@Query("SELECT um FROM UserMstrEntity um JOIN User u ON u.username = um.userName WHERE u.username = :username")
	Optional<List<UserMstrEntity>> getAllCRMsbyUsername(@Param("username") String username);
	
	@Query("SELECT u FROM UserMstrEntity u WHERE u.userId IN :userId")
	List<UserMstrEntity> getRFCApproverDetails(@Param("userId") List<Long> userId);

	@Query("SELECT u.empCode, u.clientRegEntity.clientCode FROM UserMstrEntity u WHERE u.userId = :createdBy")
	public List<Object[]> getCrmProjId(@Param("createdBy") Long createdBy);

	@Query("SELECT u.userName FROM UserMstrEntity u WHERE u.userId = :userId")
	public String getoriginator(@Param("userId")Long userId);
}