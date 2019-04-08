package com.example.demo;

import javax.persistence.*;

@Entity
public class Employee {
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private int id;
		
		@Column(nullable=true,length=20)
		private String name;
		
		@Column(nullable=true,length=20)
		private int age;
		
		@ManyToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH},optional=false)//merge:级联合并，refresh：级联刷新
		private Company company;

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

		public int getAge() {
			return age;
		}

		public void setAge(int age) {
			this.age = age;
		}

		public Company getCompany() {
			return company;
		}

		public void setCompany(Company company) {
			this.company = company;
		}
		
		
}
