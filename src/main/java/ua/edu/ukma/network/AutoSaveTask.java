package ua.edu.ukma.network;

import ua.edu.ukma.io.*;
import ua.edu.ukma.service.*;

public class AutoSaveTask implements Runnable {

    private final StudentService studentService;
    private final StudentFileService studentFileService;

    private final TeacherService teacherService;
    private final TeacherFileService teacherFileService;

    private final DepartmentService departmentService;
    private final DepartmentFileService departmentFileService;

    private final FacultyService facultyService;
    private final FacultyFileService facultyFileService;

    private final SpecialtyService specialtyService;
    private final SpecialtyFileService specialtyFileService;

    public AutoSaveTask(
            StudentService studentService,
            StudentFileService studentFileService,

            TeacherService teacherService,
            TeacherFileService teacherFileService,

            DepartmentService departmentService,
            DepartmentFileService departmentFileService,

            FacultyService facultyService,
            FacultyFileService facultyFileService,

            SpecialtyService specialtyService,
            SpecialtyFileService specialtyFileService
    ) {
        this.studentService = studentService;
        this.studentFileService = studentFileService;

        this.teacherService = teacherService;
        this.teacherFileService = teacherFileService;

        this.departmentService = departmentService;
        this.departmentFileService = departmentFileService;

        this.facultyService = facultyService;
        this.facultyFileService = facultyFileService;

        this.specialtyService = specialtyService;
        this.specialtyFileService = specialtyFileService;
    }

    @Override
    public void run() {
        while (true) {
            try {
                System.out.println("Autosaving...");

                studentFileService.saveToFile(studentService.getAll(), "data/students.json");
                teacherFileService.saveToFile(teacherService.getAll(), "data/teachers.json");
                departmentFileService.saveToFile(departmentService.getAll(), "data/departments.json");
                facultyFileService.saveToFile(facultyService.getAll(), "data/faculties.json");
                specialtyFileService.saveToFile(specialtyService.getAll(), "data/specialties.json");

                Thread.sleep(30000);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
