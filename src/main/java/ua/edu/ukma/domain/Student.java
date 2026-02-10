package ua.edu.ukma.domain;

import ua.edu.ukma.util.ValidationUtils;

public class Student extends Person {
     private String gradeBookNumber;
     private int course;
     private Specialty specialty;
     private int group;
     private int admissionYear;
     private StudyForm studyForm;
     private StudentStatus status;

    public Student(String lastName, String firstName, String middleName,
                   String gradeBookNumber, int course, int group, Specialty specialty) {
        super(lastName, firstName, middleName);
        ValidationUtils.validateNotEmpty(gradeBookNumber, "Grade book number");
        ValidationUtils.validateCourse(course);
        ValidationUtils.validateGroup(group);
        if (specialty == null) throw new IllegalArgumentException("Specialty cannot be null");
        this.gradeBookNumber = gradeBookNumber;
        this.course = course;
        this.group = group;
        this.specialty = specialty;
        this.status = StudentStatus.STUDYING;
    }


    public String getGradeBookNumber() {
         return gradeBookNumber;
    }

    public void setGradeBookNumber(String gradeBookNumber) {
        ValidationUtils.validateNotEmpty(gradeBookNumber, "Grade book number");
        this.gradeBookNumber = gradeBookNumber;
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
