package com.mkwillis.springboot2restservicebasic.service;

import com.mkwillis.springboot2restservicebasic.entity.Student;

import java.util.List;

/**
 * Created by Mark on 22/10/2018.
 */
public interface IStudentService {

    public List<Student> retrieveAllStudents();
    public Student retrieveStudent(long id);
    public void deleteStudent(long id);
    public Student saveStudent(Student student);
}
