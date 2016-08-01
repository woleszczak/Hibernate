package com.woleszczak.hibernate.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name="Employee", 
	   uniqueConstraints={@UniqueConstraint(columnNames={"ID"})})
@NamedQueries({ @NamedQuery(name = "@HQL_GET_ALL_Employee", 
query = "from Employee")/*, @NamedQuery(name = "@HQL_GET_Employee_BY_ID", 
		query = "from Employee where id=:id")*/ })
@NamedNativeQueries({ @NamedNativeQuery(name = "@SQL_GET_ALL_Employee", 
query = "select id, name, role, insert_time from Employee") })
@Cache(usage=CacheConcurrencyStrategy.READ_ONLY, region="employee")
public class Employee {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID", nullable=false, unique=true, length=11)
	private int id;
	
	@Column(name="NAME", length=20, nullable=true)
	@NotNull(message="Name cannot be null")
	@Size(min=5, max=30)
	private String name;
	
	@Column(name="ROLE", length=20, nullable=true)
	private String role;
	
	@Column(name="insert_time", nullable=true)
	private Date insertTime;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public Date getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}
	
	@Override
	public String toString() {
		return "Id= " + id + ", Name= " + name + ", Role= " + role;
	}
}
