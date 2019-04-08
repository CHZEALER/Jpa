package com.example.demo.domin;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Integer>,JpaSpecificationExecutor<User>,PagingAndSortingRepository<User,Integer>{

		@Query("select u from User u where u.username=:Username")
		User findUserByUsername(@Param("Username") String Username);
		
		@Modifying
		@Query("update User u set u.username=:username,u.password=:password where u.id=:id")
		int updateU_PById(@Param("id") int id,@Param("username") String username,@Param("password") String password);
		
		
}
