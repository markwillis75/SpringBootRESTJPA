package com.mkwillis.springboot2restservicebasic.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by Mark on 20/10/2018.
 */
@Entity
public class Student {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String passport;

    public Student(){
    }

    public Student(Long id, String name, String passport) {
        setId(id);
        setName(name);
        setPassport(passport);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", passport='" + passport + '\'' +
                '}';
    }
}
