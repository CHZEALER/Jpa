package com.example.demo;

import javax.persistence.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class C_E_Test {
	EntityManagerFactory entityManagerFactory;
	EntityManager entityManager;
	EntityTransaction entityTransaction;
	
	@Before
	public void EntityManagerFactory() {
		// 1. 创建EntityManagerFactory
		entityManagerFactory = Persistence.createEntityManagerFactory("jpa2");
		// 2. 创建EntityManager
		entityManager = entityManagerFactory.createEntityManager();
		// 3.开启事务
		entityTransaction = entityManager.getTransaction();
	}
	@After
	public void closeEntity() {
		entityManager.close();
		entityManagerFactory.close();
	}
	
	
	/*@Test
	public void insert() {
		entityTransaction.begin();
		Employee employee = new Employee();
		employee.setName("杨烨");
		employee.setAge(77);
		Employee employee2 = new Employee();
		employee2.setName("烨哥");
		employee2.setAge(88);
		
		Company company = new Company();
		company.setName("蓟县狗场");
		Company company2 = new Company();
		company2.setName("蓟县狗舍");
		
		entityManager.persist(company);
		entityManager.persist(company2);
		
		employee.setCompany(company);
		employee2.setCompany(company2);
		
		entityManager.persist(employee);
		entityManager.persist(employee2);
		
		entityTransaction.commit();
	}*/
	@Test
	public void delete() {
		entityTransaction.begin();
		
		Company company = entityManager.find(Company.class, 7);
		entityManager.remove(company);
		
		entityTransaction.commit();
	}
	/*@Test
	public void select() {
		entityTransaction.begin();
		
		Query query = entityManager.createQuery("select c From Company c where id=:id");
		query.setParameter("id", 5);
		Company company = (Company)query.getSingleResult();
		entityManager.remove(company);
		
		entityTransaction.commit();
	}*/
}
