package com.quangnv.uet.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.quangnv.uet.entities.StudentEntity;

@Repository
public interface StudentRepository extends JpaRepository<StudentEntity, String>{
	@Query("Select st From StudentEntity st Where Concat(st.firstName, st.lastName) Like %:key%")
	public Page<StudentEntity> findStudentByKey(@Param("key") String key, Pageable pageable);
	
	@Query("Select Count(*) From StudentEntity st Where Concat(st.firstName, st.lastName) Like %:key%")
	public long countByKey(@Param("key") String key);

}
