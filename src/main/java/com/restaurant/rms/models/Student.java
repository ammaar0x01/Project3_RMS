package com.restaurant.rms.models;

public class Student {
    private int id;
    private String name;
    private String surname;

    private Student(){}
    private Student(Builder builder){
        this.id = builder.builderId;
        this.name = builder.builderName;
        this.surname = builder.builderSurname;
    }
    // --------------------------------


    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getSurname() {
        return surname;
    }

    public Student copy(Student student){
        System.out.println("Object has been copied");
        this.id = student.id;
        this.name = student.name;
        this.surname = student.surname;
        System.out.println("Object Details: \n" + student);
        System.out.println("Object has been copied");
        return this;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                '}';
    }

    public static class Builder{
        private int builderId;
        private String builderName;
        private String builderSurname;

        Builder(){}
        Builder(int id, String name, String surname){
            this.builderId = id;
            this.builderName = name;
            this.builderSurname = surname;
        }
        // --------------------------------

        public Builder setBuilderId(int builderId) {
            if (builderId > 0){
                this.builderId = builderId;
            }
            return this;
        }
        public Builder setBuilderName(String builderName) {
            this.builderName = builderName;
            return this;
        }
        public Builder setBuilderSurname(String builderSurname) {
            this.builderSurname = builderSurname;
            return this;
        }

        public Student build(){
            return new Student(this);
        }

        // other copy method
        public Builder copy(Student student){
            this.builderId = student.id;
            this.builderName = student.name;
            this.builderSurname = student.surname;
            System.out.println("Object has been copied");
            return this;
        }
    }
}
