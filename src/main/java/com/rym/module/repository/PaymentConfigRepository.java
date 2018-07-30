package com.rym.module.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rym.module.entity.PaymentConfigEntity;

@Repository
public interface PaymentConfigRepository extends JpaRepository<PaymentConfigEntity, Long>{

}
