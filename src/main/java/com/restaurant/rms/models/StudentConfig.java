package com.restaurant.rms.models;

import java.util.ArrayList;

public class StudentConfig {
    public ArrayList<Student> objects = new ArrayList<>();

    public void generateObjects(){
        Student student1 = new Student.Builder()
                .setBuilderId(1)
                .setBuilderName("Shanty")
                .setBuilderSurname("Niel")
                .build();

        Student student2 = new Student.Builder(2, "Hans", "Low").build();
        Student student3 = new Student.Builder(3, "Wybie", "Neck").build();
        Student student4 = new Student.Builder(4, "Coraline", "Buttons").build();
        Student student5 = new Student.Builder(-1, "Julie", "Anne").build();

        objects.add(student1);
        objects.add(student2);
        objects.add(student3);
        objects.add(student4);
        objects.add(student5);
        System.out.println("\nObjects added to the arraylist");
    }
}
