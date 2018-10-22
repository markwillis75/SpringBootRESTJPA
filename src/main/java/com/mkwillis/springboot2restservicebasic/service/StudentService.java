package com.mkwillis.springboot2restservicebasic.service;

import com.mkwillis.springboot2restservicebasic.entity.Student;
import com.mkwillis.springboot2restservicebasic.entity.StudentNotFoundException;
import com.mkwillis.springboot2restservicebasic.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Created by Mark on 22/10/2018.
 */
@Service
public class StudentService implements IStudentService{
    @Autowired
    private StudentRepository studentRepository;

    public List<Student> retrieveAllStudents(){
        return studentRepository.findAll();
    }

    public Student retrieveStudent(long id){
        Optional<Student> student = studentRepository.findById(id);

        if (!student.isPresent()){
            throw new StudentNotFoundException("id: " + id);
        }

        return student.get();
    }

    public void deleteStudent(long id){
        studentRepository.deleteById(id);
    }

    public Student saveStudent(Student student){
        return studentRepository.save(student);
    }
}
