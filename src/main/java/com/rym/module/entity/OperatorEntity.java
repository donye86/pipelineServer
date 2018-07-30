package com.rym.module.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.domain.Persistable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "base_operator")
@ApiModel(description="运营商实体")
@Getter
@Setter
public class OperatorEntity implements Persistable<Integer> {

	private static final long serialVersionUID = 1L;


	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fd_id", nullable = false, unique = true, insertable = false, updatable = false)
	@ApiModelProperty(notes = "主键，唯一标识", dataType = "Integer")
    private Integer id;
	
	@Column(name = "fd_name",length=50)
	@ApiModelProperty(notes = "运营商名称", dataType = "String")
	private String name;
	
	@Column(name = "fd_mobile",length=20)
	@ApiModelProperty(notes = "运营商联系号码", dataType = "String")
	private String mobile;
	
	@Column(name = "fd_address",length=100)
	@ApiModelProperty(notes = "运营商联系地址", dataType = "String")
	private String address;
	
	@Column(name = "fd_introduce",length=255)
	@ApiModelProperty(notes = "运营商联系地址", dataType = "String")
	private String introduce;
	
	@Column(name = "fd_manager",length=255)
	@ApiModelProperty(notes = "运营商管理员姓名", dataType = "String")
	private String manager;
	
	@ApiModelProperty(notes = "是否删除，0，否，1是", dataType = "String")
	@Column(name = "fd_isDel",length=4)
	private Integer isDel;
	
	@JsonIgnore
    @Override
    public boolean isNew() {
        return null == id;
    }

}
