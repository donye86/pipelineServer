package com.rym.module.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.rym.module.entity.PaymentWayConfigEntity;

@Repository
public interface PaymentWayConfigRepository extends JpaRepository<PaymentWayConfigEntity, Long>{
	@Query(value="SELECT b.id,b.name,b.3rd_party_payment,a.sort FROM washer_payment_config a " + 
			"left join payment_way_config b on a.payment_way_id=b.id " + 
			"where a.campus_id = ?1", nativeQuery = true)
	public List<PaymentWayConfigEntity> getPaymentConfig(Integer campusId);
	
}
