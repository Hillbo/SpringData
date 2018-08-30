package com.sdata.mapper;

import com.sdata.pojo.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StudentDao extends JpaRepository<Student, String>, JpaSpecificationExecutor<Student>{

    @Query("SELECT student FROM Student student LEFT JOIN Asset asset ON student.id = asset.id WHERE student.id=:#{#student.id}")
    Student findById(@Param("student") Student student);

    @Modifying
    @Query("DELETE FROM Student student WHERE student.id = :id")
    void deleteById(@Param("id") String id);
}
