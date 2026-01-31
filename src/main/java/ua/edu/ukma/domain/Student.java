package ua.edu.ukma.domain;

 public class Student extends Person {
     private String studentId;
     private int course;
     private String group;
     private int admissionYear;
     private StudyForm studyForm;
     private StudentStatus status;

     public Student(String lastName, String firstName, String middleName,
                    String studentId, int course, String group) {
         super(lastName, firstName, middleName);
         this.studentId = studentId;
         this.course = course;
         this.group = group;
         this.status = StudentStatus.STUDYING;
     }

     public int getCourse() {
         return course;
     }

     enum StudyForm {
         BUDGET, CONTRACT
     }


     enum StudentStatus {
         STUDYING, ACADEMIC_LEAVE, EXPELLED
     }


}
