package ua.edu.ukma.network;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import ua.edu.ukma.auth.AuthService;
import ua.edu.ukma.service.DepartmentService;
import ua.edu.ukma.service.FacultyService;
import ua.edu.ukma.service.SpecialtyService;
import ua.edu.ukma.service.StudentService;
import ua.edu.ukma.service.TeacherService;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TcpServer {
    private final int port;
    private final CommandProcessor processor;
    private final ObjectMapper mapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    private final ExecutorService pool = Executors.newFixedThreadPool(10);

    public TcpServer(int port, AuthService authService, FacultyService facultyService, DepartmentService departmentService, SpecialtyService specialtyService, StudentService studentService, TeacherService teacherService) {
        this.port = port;
        this.processor = new CommandProcessor(authService, facultyService, departmentService, specialtyService, studentService, teacherService);
    }

    public void start() throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("TCP server started on port " + port);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getRemoteSocketAddress());
                pool.submit(new ClientHandler(clientSocket, processor, mapper));
            }
        }
    }
}