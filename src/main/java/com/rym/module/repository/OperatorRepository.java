package com.rym.module.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rym.module.entity.OperatorEntity;

@Repository
public interface OperatorRepository extends JpaRepository<OperatorEntity, Long>{

	public List<OperatorEntity> findByIsDel(Integer isDel);
	
}
