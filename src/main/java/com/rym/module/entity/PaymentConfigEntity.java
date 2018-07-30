package com.rym.module.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.Persistable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "washer_payment_config")
@ApiModel(description = "支付关系配置")
@Getter
@Setter
public class PaymentConfigEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, unique = true, insertable = false, updatable = false)
	@ApiModelProperty(notes = "主键，唯一标识", dataType = "Integer")
	private Integer id;

	@Column(name = "payment_way_id")
	@ApiModelProperty(notes = "关联的支付方式ID", dataType = "Integer")
	private Integer paymentWayId;

	@Column(name = "payment_way_name")
	@ApiModelProperty(notes = "关联的支付方式名称", dataType = "String")
	private String paymentWayName;

	// @Column(name = "operator_id")
	// @ApiModelProperty(notes = "运营商ID", dataType = "Integer")
	// private Integer operatorId;

	// @Column(name = "operator_name")
	// @ApiModelProperty(notes = "运营商名称", dataType = "String")
	// private String operatorName;

	@Column(name = "campus_id")
	@ApiModelProperty(notes = "校区ID", dataType = "Integer")
	private Integer campusId;

	@Column(name = "campus_name")
	@ApiModelProperty(notes = "校区名称", dataType = "String")
	private String campusName;

	// @Column(name = "service_id")
	// @ApiModelProperty(notes = "服务ID", dataType = "Integer")
	// private Integer serviceId;
	//
	// @Column(name = "service_name")
	// @ApiModelProperty(notes = "服务名称", dataType = "String")
	// private String serviceName;

	@Column(name = "enabled", nullable = false)
	@ApiModelProperty(notes = "是否启用该支付方式：0-否，1-是，不启用的校区在APP端将不会展示该支付方式", dataType = "Boolean")
	private Boolean enabled;

	@Column(name = "sort", nullable = false)
	@ApiModelProperty(notes = "支付方式在APP端的排位顺序，值越小越靠前，0值仅预留给艾米余额支付，其他支付方式的sort值均为大于0的正整数", dataType = "Integer")
	private Integer sort;

	@CreatedDate
	@Column(name = "created_at")
	@ApiModelProperty(notes = "创建时间", dataType = "Integer")
	private Date createdAt;

	@LastModifiedDate
	@Column(name = "updated_at")
	@ApiModelProperty(notes = "更新时间", dataType = "Integer")
	private Date updatedAt;

}
