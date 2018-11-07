package com.cj.anotation_spring.pojo;


import java.util.Date;

public class Entity implements java.io.Serializable {

	private Integer id;
	private Date createTime;

	public Integer getId() {
		return id;
	}

	public Entity setId(Integer id) {
		this.id = id;
		return this;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public Entity setCreateTime(Date createTime) {
		this.createTime = createTime;
		return this;
	}
}
