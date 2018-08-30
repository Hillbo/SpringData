package com.sdata.pojo;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

@Data
@Entity
public class Student {

    @Id
    private String id;

    private String name;

    private Integer age;

    @Transient
    private String f_name;

    public Student() {
    }

    public Student(String id, String name, Integer age, String f_name) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.f_name = f_name;
    }
}
