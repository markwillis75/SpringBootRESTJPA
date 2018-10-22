package com.mkwillis.springboot2restservicebasic.resource;

import com.mkwillis.springboot2restservicebasic.entity.Student;
import com.mkwillis.springboot2restservicebasic.entity.StudentNotFoundException;
import com.mkwillis.springboot2restservicebasic.service.IStudentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

/**
 * Created by Mark on 20/10/2018.
 */
@RestController
public class StudentResource {

    @Autowired
    private IStudentService studentService;

    @GetMapping("/students")
    public List<Student> retrieveAllStudents(){
        return studentService.retrieveAllStudents();
    }

    @GetMapping("/students/{id}")
    public Student retrieveStudent(@PathVariable long id){
        return studentService.retrieveStudent(id);
    }

    @DeleteMapping("/students/{id}")
    public void deleteStudent(@PathVariable long id){
        studentService.deleteStudent(id);
    }

    @PostMapping("/students")
    public ResponseEntity<Object> createStudent(@RequestBody Student student){
        Student savedStudent = studentService.saveStudent(student);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedStudent.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/students/{id}")
    public ResponseEntity<Object> updateStudent(@RequestBody Student student, @PathVariable Long id){
        Student retrievedStudent;

        try {
            retrievedStudent = studentService.retrieveStudent(id);
        }
        catch (StudentNotFoundException stnfe){
            return ResponseEntity.notFound().build();
        }

        student.setId(id);
        studentService.saveStudent(student);

        return ResponseEntity.noContent().build();
    }
}
