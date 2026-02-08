package ua.edu.ukma.domain;

public class Student extends Person {
     private String studentId;
     private int course;
     private Specialty specialty;
     private int group;
     private int admissionYear;
     private StudyForm studyForm;
     private StudentStatus status;

     public Student(String lastName, String firstName, String middleName,
                    String studentId, int course, int group, Specialty specialty) {
         super(lastName, firstName, middleName);
         this.studentId = studentId;
         this.course = course;
         this.group = group;
         this.specialty = specialty;
         this.status = StudentStatus.STUDYING;
     }

     public String getStudentId() {
         return studentId;
     }

     public void setStudentId(String studentId) {
         if (studentId == null || studentId.isBlank()) throw new IllegalArgumentException("Student ID cannot be empty");
         this.studentId = studentId;
     }

     public int getCourse() {
         return course;
     }

     public void setCourse(int course) {
         if (course < 1 || course > 6) throw new IllegalArgumentException("Course must be between 1 and 6");
         this.course = course;
     }

     public Specialty getSpecialty() {
         return specialty;
     }

     public void setSpecialty(Specialty specialty) {
         this.specialty = specialty;
     }

     public int getGroup() {
         return group;
     }

     public void setGroup(int group) {
         this.group = group;
     }

     public int getAdmissionYear() {
         return admissionYear;
     }

     public void setAdmissionYear(int admissionYear) {
         if (admissionYear < 1900) throw new IllegalArgumentException("Invalid admission year");
         this.admissionYear = admissionYear;
     }

     public StudyForm getStudyForm() {
         return studyForm;
     }

     public void setStudyForm(StudyForm studyForm) {
         if (studyForm == null) throw new IllegalArgumentException("Study form cannot be null");
         this.studyForm = studyForm;
     }

     public StudentStatus getStatus() {
         return status;
     }

     public void setStatus(StudentStatus status) {
         if (status == null) throw new IllegalArgumentException("Student status cannot be null");
         this.status = status;
     }

}
