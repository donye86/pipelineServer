package com.rym.module.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "payment_way_config")
@ApiModel(description = "支付方式配置")
@Getter
@Setter
public class PaymentWayConfigEntity{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, unique = true, insertable = false, updatable = false)
	@ApiModelProperty(notes = "主键，唯一标识", dataType = "Integer")
	private Integer id;

	@Column(name = "name", nullable = false)
	@ApiModelProperty(notes = "支付名称", dataType = "String")
	private String name;

	@Column(name = "3rd_party_payment", nullable = false)
	@ApiModelProperty(notes = "支付分組", dataType = "Integer")
	private Integer groupId;

	@Column(name = "sort", nullable = false)
	@ApiModelProperty(notes = "支付方式在APP端的排位顺序，值越小越靠前，0值仅预留给艾米余额支付，其他支付方式的sort值均为大于0的正整数", dataType = "Integer")
	private Integer sort;

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
