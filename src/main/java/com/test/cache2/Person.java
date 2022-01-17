package com.test.cache2;

import lombok.Data;

@Data
public class Person{
    Integer age;
    String name;
    public Person(Integer age, String name) {
        this.age = age;
        this.name = name;
    }
}
