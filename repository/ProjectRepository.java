package com.rajutech.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rajutech.project.dao.model.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long>{

}
