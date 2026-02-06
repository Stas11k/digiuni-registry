package ua.edu.ukma.domain;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

public class Teacher extends Person {
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

     public String getPosition() {
         return position;
     }

    public void setPosition(String position) {
        if (position == null || position.isBlank()) throw new IllegalArgumentException("Position cannot be empty");
        this.position = position;
    }

     public String getDegree() {
         return degree;
     }

     public void setDegree(String degree) {
         this.degree = degree;
     }

     public String getTitle() {
         return title;
     }

     public void setTitle(String title) {
         this.title = title;
     }

     public LocalDate getHireDate() {
         return hireDate;
     }

    public void setHireDate(LocalDate hireDate) {
        if (hireDate == null || hireDate.isAfter(LocalDate.now())) throw new IllegalArgumentException("Invalid hire date");
        this.hireDate = hireDate;
    }

     public double getWorkload() {
         return workload;
     }

    public void setWorkload(double workload) {
        if (workload < 0) throw new IllegalArgumentException("Workload cannot be negative");
        this.workload = workload;
    }

}
