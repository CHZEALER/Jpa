package com.example.demo;

import java.util.List;

import javax.persistence.*;

@Entity
public class Company {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable=true,length=20)
	private String name;
	
	@OneToMany(mappedBy="company",cascade=CascadeType.ALL,fetch=FetchType.LAZY)//懒加载，加载一个实体时，定义懒加载的属性不会马上从数据库中加载
	private List<Employee> employeeList;

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

	public List<Employee> getEmployeeList() {
		return employeeList;
	}

	public void setEmployeeList(List<Employee> employeeList) {
		this.employeeList = employeeList;
	}
}
