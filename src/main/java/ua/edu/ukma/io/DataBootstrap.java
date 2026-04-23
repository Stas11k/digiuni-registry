package ua.edu.ukma.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.edu.ukma.converter.*;
import ua.edu.ukma.domain.*;
import ua.edu.ukma.dto.*;
import ua.edu.ukma.repository.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataBootstrap {
    private static final Logger logger = LoggerFactory.getLogger(DataBootstrap.class);

    private final FacultyFileService facultyFileService = new FacultyFileService();
    private final DepartmentFileService departmentFileService = new DepartmentFileService();
    private final SpecialtyFileService specialtyFileService = new SpecialtyFileService();
    private final TeacherFileService teacherFileService = new TeacherFileService();
    private final StudentFileService studentFileService = new StudentFileService();
    private final UniversityFileService universityFileService = new UniversityFileService();

    public University loadAll(Repository<Faculty, Integer> facultyRepo, Repository<Department, Integer> departmentRepo, Repository<Specialty, Integer> specialtyRepo, Repository<Teacher, Integer> teacherRepo, Repository<Student, Integer> studentRepo) {
        logger.info("Starting bootstrap loading");

        facultyRepo.clear();
        departmentRepo.clear();
        specialtyRepo.clear();
        teacherRepo.clear();
        studentRepo.clear();

        Faculty.resetCounter();
        Department.resetCounter();
        Specialty.resetCounter();
        Teacher.resetCounter();
        Student.resetCounter();

        Map<Integer, Faculty> facultyMap = new HashMap<>();
        Map<Integer, Department> departmentMap = new HashMap<>();
        Map<Integer, Specialty> specialtyMap = new HashMap<>();
        Map<Integer, Teacher> teacherMap = new HashMap<>();

        List<FacultyDTO> facultyDTOs = facultyFileService.loadDTOs("faculties.json");
        for (FacultyDTO dto : facultyDTOs) {
            Faculty faculty = FacultyMapper.fromDTO(dto);
            facultyRepo.save(faculty);
            facultyMap.put(faculty.getId(), faculty);
        }

        List<DepartmentDTO> departmentDTOs = departmentFileService.loadDTOs("departments.json");
        for (DepartmentDTO dto : departmentDTOs) {
            Faculty faculty = facultyMap.get(dto.facultyId());
            if (faculty != null) {
                Department department = DepartmentMapper.fromDTO(dto, faculty);
                departmentRepo.save(department);
                departmentMap.put(department.getId(), department);
            }
        }

        List<SpecialtyDTO> specialtyDTOs = specialtyFileService.loadDTOs("specialties.json");
        for (SpecialtyDTO dto : specialtyDTOs) {
            Department department = departmentMap.get(dto.departmentId());
            if (department != null) {
                Specialty specialty = SpecialtyMapper.fromDTO(dto, department);
                specialtyRepo.save(specialty);
                specialtyMap.put(specialty.getId(), specialty);
            }
        }

        List<TeacherDTO> teacherDTOs = teacherFileService.loadDTOs("teachers.json");
        for (TeacherDTO dto : teacherDTOs) {
            Department department = departmentMap.get(dto.departmentId());
            if (department != null) {
                Teacher teacher = TeacherMapper.fromDTO(dto, department);
                teacherRepo.save(teacher);
                teacherMap.put(teacher.getId(), teacher);
            }
        }

        for (FacultyDTO dto : facultyDTOs) {
            if (dto.deanId() != null) {
                Faculty faculty = facultyMap.get(dto.id());
                Teacher dean = teacherMap.get(dto.deanId());
                if (faculty != null && dean != null) {
                    faculty.setDean(dean);
                }
            }
        }

        for (DepartmentDTO dto : departmentDTOs) {
            if (dto.headId() != null) {
                Department department = departmentMap.get(dto.id());
                Teacher head = teacherMap.get(dto.headId());
                if (department != null && head != null) {
                    department.setHead(head);
                }
            }
        }

        List<StudentDTO> studentDTOs = studentFileService.loadDTOs("students.json");
        for (StudentDTO dto : studentDTOs) {
            Specialty specialty = specialtyMap.get(dto.specialtyId());
            if (specialty != null) {
                Student student = StudentMapper.fromDTO(dto, specialty);
                studentRepo.save(student);
            }
        }

        University university = universityFileService.loadFromFile("university.json");
        if (university == null) {
            logger.warn("University file not found. Using default university.");
            university = new University("Kyiv-Mohyla Academy", "NaUKMA", "Kyiv", "2 Hryhorii Skovoroda St.");
        }
        logger.info("Bootstrap loading completed");
        return university;
    }
}