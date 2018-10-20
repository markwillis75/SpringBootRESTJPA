package com.mkwillis.springboot2restservicebasic.resource;

import com.mkwillis.springboot2restservicebasic.entity.Student;
import com.mkwillis.springboot2restservicebasic.entity.StudentNotFoundException;
import com.mkwillis.springboot2restservicebasic.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Created by Mark on 20/10/2018.
 */
@RestController
public class StudentResource {

    @Autowired
    private StudentRepository studentRepository;

    @GetMapping("/students")
    public List<Student> retrieveAllStudents(){
        return studentRepository.findAll();
    }

    @GetMapping("/students/{id}")
    public Student retrieveStudent(@PathVariable long id){
        Optional<Student> student = studentRepository.findById(id);

        if (!student.isPresent()){
            throw new StudentNotFoundException("id: " + id);
        }

        return student.get();
    }

    @DeleteMapping("/students/{id}")
    public void deleteStudent(@PathVariable long id){
        studentRepository.deleteById(id);
    }

    @PostMapping("/students")
    public ResponseEntity<Object> createStudent(@RequestBody Student student){
        Student savedStudent = studentRepository.save(student);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedStudent.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/students/{id}")
    public ResponseEntity<Object> updateStudent(@RequestBody Student student, @PathVariable Long id){
        Optional<Student> retrievedStudent = studentRepository.findById(id);

        if (!retrievedStudent.isPresent()){
            return ResponseEntity.notFound().build();
        }

        student.setId(id);
        studentRepository.save(student);

        return ResponseEntity.noContent().build();
    }
}
