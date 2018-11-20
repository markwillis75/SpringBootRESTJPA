package com.mkwillis.springboot2restservicebasic.repository;

import com.mkwillis.springboot2restservicebasic.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Mark on 20/10/2018.
 */
@Repository
public interface StudentRepository extends JpaRepository<Student, Long>{
}
