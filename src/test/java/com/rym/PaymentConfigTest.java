package com.rym;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.rym.module.entity.PaymentConfigEntity;
import com.rym.module.repository.OperatorRepository;
import com.rym.module.repository.PaymentConfigRepository;
import com.rym.module.repository.PaymentWayConfigRepository;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class PaymentConfigTest extends BaseTest {
	@Autowired
	private PaymentConfigRepository paymentConfigRepository;
	
	@Autowired
	private OperatorRepository operatorRepository;
	
	@Autowired
	private PaymentWayConfigRepository paymentWayConfigRepository;

	@Test
	public void test() {
		log.debug("单元测试开始");
		PaymentConfigEntity entity = new PaymentConfigEntity();
		entity.setCampusId(1);
		entity.setCampusName("测试校区");
		entity.setEnabled(true);
//		entity.setOperatorId(1l);
//		entity.setOperatorName("测试运营商");
		entity.setPaymentWayId(1);
		entity.setPaymentWayName("微信支付");
//		entity.setServiceId(1l);
//		entity.setServiceName("洗衣");
		entity.setSort(1);
		log.debug(paymentConfigRepository.save(entity));
	}
	
	@Test
	public void delete() {
		paymentConfigRepository.delete(2215l);
	}
	
	@Test
	public void queryPaymentTest() {
		log.debug(paymentWayConfigRepository.getPaymentConfig(36));
	}
}
