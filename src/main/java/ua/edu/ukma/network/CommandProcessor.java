package ua.edu.ukma.network;

import ua.edu.ukma.auth.AuthService;
import ua.edu.ukma.auth.Permission;
import ua.edu.ukma.auth.User;
import ua.edu.ukma.converter.*;
import ua.edu.ukma.domain.*;
import ua.edu.ukma.exception.EntityNotFoundException;
import ua.edu.ukma.exception.ValidationException;
import ua.edu.ukma.service.DepartmentService;
import ua.edu.ukma.service.FacultyService;
import ua.edu.ukma.service.SpecialtyService;
import ua.edu.ukma.service.StudentService;
import ua.edu.ukma.service.TeacherService;

import java.util.List;
import java.util.Map;

public class CommandProcessor {
    private final AuthService authService;
    private final FacultyService facultyService;
    private final DepartmentService departmentService;
    private final SpecialtyService specialtyService;
    private final StudentService studentService;
    private final TeacherService teacherService;

    public CommandProcessor(AuthService authService, FacultyService facultyService, DepartmentService departmentService, SpecialtyService specialtyService, StudentService studentService, TeacherService teacherService) {
        this.authService = authService;
        this.facultyService = facultyService;
        this.departmentService = departmentService;
        this.specialtyService = specialtyService;
        this.studentService = studentService;
        this.teacherService = teacherService;
    }

    public Response process(Request request, SessionContext session) {
        try {
            return switch (request.command()) {
                case "LOGIN" -> handleLogin(request, session);
                case "LOGOUT" -> handleLogout(session);
                case "FACULTY_LIST" -> requirePermission(session, Permission.VIEW_FACULTIES,
                        Response.ok("Faculties", facultyService.getAll().stream()
                                .map(FacultyMapper::toDTO)
                                .toList()));
                case "DEPARTMENT_LIST" -> requirePermission(session, Permission.VIEW_DEPARTMENTS,
                        Response.ok("Departments", departmentService.getAll().stream()
                                .map(DepartmentMapper::toDTO)
                                .toList()));
                case "SPECIALTY_LIST" -> requirePermission(session, Permission.VIEW_SPECIALTIES,
                        Response.ok("Specialties", specialtyService.getAll().stream()
                                .map(SpecialtyMapper::toDTO)
                                .toList()));
                case "STUDENT_LIST" -> requirePermission(session, Permission.VIEW_STUDENTS,
                        Response.ok("Students", studentService.getAll().stream()
                                .map(StudentMapper::toDTO)
                                .toList()));
                case "TEACHER_LIST" -> requirePermission(session, Permission.VIEW_TEACHERS,
                        Response.ok("Teachers", teacherService.getAll().stream()
                                .map(TeacherMapper::toDTO)
                                .toList()));
                case "FACULTY_ADD" -> handleFacultyAdd(request, session);
                case "FACULTY_DELETE" -> handleFacultyDelete(request, session);
                case "DEPARTMENT_ADD" -> handleDepartmentAdd(request, session);
                case "DEPARTMENT_DELETE" -> handleDepartmentDelete(request, session);
                case "SPECIALTY_ADD" -> handleSpecialtyAdd(request, session);
                case "SPECIALTY_DELETE" -> handleSpecialtyDelete(request, session);
                case "STUDENT_ADD" -> handleStudentAdd(request, session);
                case "STUDENT_DELETE" -> handleStudentDelete(request, session);
                case "STUDENT_FIND_BY_NAME" -> handleStudentFindByName(request, session);
                case "TEACHER_ADD" -> handleTeacherAdd(request, session);
                case "TEACHER_DELETE" -> handleTeacherDelete(request, session);
                case "TEACHER_FIND_BY_NAME" -> handleTeacherFindByName(request, session);
                default -> Response.error("Unknown command: " + request.command());
            };
        } catch (ValidationException | EntityNotFoundException e) {
            return Response.error(e.getMessage());
        } catch (Exception e) {
            return Response.error("Server error: " + e.getMessage());
        }
    }

    private Response handleLogin(Request request, SessionContext session) {
        User user = authService.login(request.login(), request.password());
        if (user == null) {
            return Response.error("Invalid credentials");
        }
        session.setCurrentUser(user);
        return Response.ok("Logged in as " + user.getLogin(), user.getRole().name());
    }

    private Response handleLogout(SessionContext session) {
        session.logout();
        return Response.ok("Logged out");
    }

    private Response handleFacultyAdd(Request request, SessionContext session) {
        User user = requireAuth(session);
        if (!user.hasPermission(Permission.EDIT_FACULTIES)) {
            return Response.error("Access denied");
        }
        Map<String, String> p = requireParams(request);
        String name = requireParam(p, "name");
        String shortName = requireParam(p, "shortName");
        Faculty faculty = new Faculty(name, shortName);
        facultyService.add(faculty);
        return Response.ok("Faculty added", FacultyMapper.toDTO(faculty));
    }

    private Response handleFacultyDelete(Request request, SessionContext session) {
        User user = requireAuth(session);
        if (!user.hasPermission(Permission.EDIT_FACULTIES)) {
            return Response.error("Access denied");
        }
        Map<String, String> p = requireParams(request);
        int id = Integer.parseInt(requireParam(p, "id"));
        boolean deleted = facultyService.delete(id);
        return deleted ? Response.ok("Faculty deleted") : Response.error("Faculty not found");
    }

    private Response handleDepartmentAdd(Request request, SessionContext session) {
        User user = requireAuth(session);
        if (!user.hasPermission(Permission.EDIT_DEPARTMENTS)) {
            return Response.error("Access denied");
        }
        Map<String, String> p = requireParams(request);
        String name = requireParam(p, "name");
        int facultyId = Integer.parseInt(requireParam(p, "facultyId"));
        Faculty faculty = facultyService.getOrThrow(facultyId);
        Department department = new Department(name, faculty);
        departmentService.add(department);
        return Response.ok("Department added", DepartmentMapper.toDTO(department));
    }

    private Response handleDepartmentDelete(Request request, SessionContext session) {
        User user = requireAuth(session);
        if (!user.hasPermission(Permission.EDIT_DEPARTMENTS)) {
            return Response.error("Access denied");
        }
        Map<String, String> p = requireParams(request);
        int id = Integer.parseInt(requireParam(p, "id"));
        boolean deleted = departmentService.delete(id);
        return deleted ? Response.ok("Department deleted") : Response.error("Department not found");
    }

    private Response handleSpecialtyAdd(Request request, SessionContext session) {
        User user = requireAuth(session);
        if (!user.hasPermission(Permission.EDIT_SPECIALTIES)) {
            return Response.error("Access denied");
        }
        Map<String, String> p = requireParams(request);
        String name = requireParam(p, "name");
        int departmentId = Integer.parseInt(requireParam(p, "departmentId"));
        Department department = departmentService.getOrThrow(departmentId);
        Specialty specialty = new Specialty(name, department);
        specialtyService.add(specialty);
        return Response.ok("Specialty added", SpecialtyMapper.toDTO(specialty));
    }

    private Response handleSpecialtyDelete(Request request, SessionContext session) {
        User user = requireAuth(session);
        if (!user.hasPermission(Permission.EDIT_SPECIALTIES)) {
            return Response.error("Access denied");
        }
        Map<String, String> p = requireParams(request);
        int id = Integer.parseInt(requireParam(p, "id"));
        boolean deleted = specialtyService.delete(id);
        return deleted ? Response.ok("Specialty deleted") : Response.error("Specialty not found");
    }

    private Response handleStudentAdd(Request request, SessionContext session) {
        User user = requireAuth(session);
        if (!user.hasPermission(Permission.EDIT_STUDENTS)) {
            return Response.error("Access denied");
        }
        Map<String, String> p = requireParams(request);
        String lastName = requireParam(p, "lastName");
        String firstName = requireParam(p, "firstName");
        String middleName = requireParam(p, "middleName");
        String gradeBookNumber = requireParam(p, "gradeBookNumber");
        int course = Integer.parseInt(requireParam(p, "course"));
        int group = Integer.parseInt(requireParam(p, "group"));
        int specialtyId = Integer.parseInt(requireParam(p, "specialtyId"));
        Specialty specialty = specialtyService.getOrThrow(specialtyId);
        Student student = new Student(lastName, firstName, middleName, gradeBookNumber, course, group, specialty);
        studentService.add(student);
        return Response.ok("Student added", StudentMapper.toDTO(student));
    }

    private Response handleStudentDelete(Request request, SessionContext session) {
        User user = requireAuth(session);
        if (!user.hasPermission(Permission.EDIT_STUDENTS)) {
            return Response.error("Access denied");
        }
        Map<String, String> p = requireParams(request);
        int id = Integer.parseInt(requireParam(p, "id"));
        boolean deleted = studentService.delete(id);
        return deleted ? Response.ok("Student deleted") : Response.error("Student not found");
    }

    private Response handleStudentFindByName(Request request, SessionContext session) {
        User user = requireAuth(session);
        if (!user.hasPermission(Permission.VIEW_STUDENTS)) {
            return Response.error("Access denied");
        }
        Map<String, String> p = requireParams(request);
        String query = requireParam(p, "query");
        List<Student> result = studentService.findByFullName(query);
        return Response.ok("Students found", result.stream()
                .map(StudentMapper::toDTO)
                .toList());
    }

    private Response handleTeacherAdd(Request request, SessionContext session) {
        User user = requireAuth(session);
        if (!user.hasPermission(Permission.EDIT_TEACHERS)) {
            return Response.error("Access denied");
        }
        Map<String, String> p = requireParams(request);
        String lastName = requireParam(p, "lastName");
        String firstName = requireParam(p, "firstName");
        String middleName = requireParam(p, "middleName");
        String position = requireParam(p, "position");
        int departmentId = Integer.parseInt(requireParam(p, "departmentId"));
        Department department = departmentService.getOrThrow(departmentId);
        Teacher teacher = new Teacher(lastName, firstName, middleName, position, department);
        teacherService.add(teacher);
        return Response.ok("Teacher added", TeacherMapper.toDTO(teacher));
    }

    private Response handleTeacherDelete(Request request, SessionContext session) {
        User user = requireAuth(session);
        if (!user.hasPermission(Permission.EDIT_TEACHERS)) {
            return Response.error("Access denied");
        }
        Map<String, String> p = requireParams(request);
        int id = Integer.parseInt(requireParam(p, "id"));
        boolean deleted = teacherService.delete(id);
        return deleted ? Response.ok("Teacher deleted") : Response.error("Teacher not found");
    }

    private Response handleTeacherFindByName(Request request, SessionContext session) {
        User user = requireAuth(session);
        if (!user.hasPermission(Permission.VIEW_TEACHERS)) {
            return Response.error("Access denied");
        }
        Map<String, String> p = requireParams(request);
        String query = requireParam(p, "query");
        List<Teacher> result = teacherService.findByFullName(query);
        return Response.ok("Teachers found", result.stream()
                .map(TeacherMapper::toDTO)
                .toList());
    }

    private User requireAuth(SessionContext session) {
        if (!session.isAuthenticated()) {
            throw new ValidationException("You must log in first");
        }
        return session.getCurrentUser();
    }

    private Response requirePermission(SessionContext session, int permission, Response successResponse) {
        User user = requireAuth(session);
        if (!user.hasPermission(permission)) {
            return Response.error("Access denied");
        }
        return successResponse;
    }

    private Map<String, String> requireParams(Request request) {
        if (request.params() == null) {
            throw new ValidationException("Request params cannot be null");
        }
        return request.params();
    }

    private String requireParam(Map<String, String> params, String key) {
        String value = params.get(key);
        if (value == null || value.isBlank()) {
            throw new ValidationException("Missing required param: " + key);
        }
        return value;
    }
}