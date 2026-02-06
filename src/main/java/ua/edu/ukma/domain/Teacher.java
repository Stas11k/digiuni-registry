package ua.edu.ukma.domain;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

 class Teacher extends Person {
     private String position;
     private String degree;
     private String title;
     private LocalDate hireDate;
     private double workload;
     public Teacher(String lastName, String firstName, String middleName,
                    String position) {
         super(lastName, firstName, middleName);
         this.position = position;
         this.hireDate = LocalDate.now();
     }


}
