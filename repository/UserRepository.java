package com.rajutech.project.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rajutech.project.dao.model.User;

import jakarta.transaction.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUsername(String username);

	Boolean existsByUsername(String username);

	Boolean existsByEmail(String email);

	@Modifying
	@Transactional
	@Query("UPDATE User u SET u.password = :password WHERE u.username IN (SELECT um.userName FROM UserMstrEntity um WHERE um.userName = :username AND um.clientRegEntity.clientId = :crmId)")
	int changePassword(@Param("username") String username, @Param("password") String password,
			@Param("crmId") String crmId);


}
