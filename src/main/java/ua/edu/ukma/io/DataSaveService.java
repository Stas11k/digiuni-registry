package ua.edu.ukma.io;

import ua.edu.ukma.domain.*;
import ua.edu.ukma.repository.Repository;

public class DataSaveService {

    private final FacultyFileService facultyFileService = new FacultyFileService();
    private final DepartmentFileService departmentFileService = new DepartmentFileService();
    private final SpecialtyFileService specialtyFileService = new SpecialtyFileService();
    private final TeacherFileService teacherFileService = new TeacherFileService();
    private final StudentFileService studentFileService = new StudentFileService();
    private final UniversityFileService universityFileService = new UniversityFileService();

    public void saveAll(Repository<Faculty, Integer> facultyRepo, Repository<Department, Integer> departmentRepo, Repository<Specialty, Integer> specialtyRepo, Repository<Teacher, Integer> teacherRepo, Repository<Student, Integer> studentRepo, University university) {
        facultyFileService.saveToFile(facultyRepo.findAll(), DataPaths.FACULTIES);
        departmentFileService.saveToFile(departmentRepo.findAll(), DataPaths.DEPARTMENTS);
        specialtyFileService.saveToFile(specialtyRepo.findAll(), DataPaths.SPECIALTIES);
        teacherFileService.saveToFile(teacherRepo.findAll(), DataPaths.TEACHERS);
        studentFileService.saveToFile(studentRepo.findAll(), DataPaths.STUDENTS);
        universityFileService.saveToFile(university, DataPaths.UNIVERSITY);
    }
}