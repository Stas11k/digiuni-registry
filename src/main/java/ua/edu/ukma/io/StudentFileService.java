package ua.edu.ukma.io;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import ua.edu.ukma.converter.StudentMapper;
import ua.edu.ukma.domain.Student;
import ua.edu.ukma.dto.StudentDTO;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class StudentFileService {

    private final ObjectMapper mapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .enable(SerializationFeature.INDENT_OUTPUT);

    public void saveToFile(List<Student> students, String filePath) {
        try {
            List<StudentDTO> dtos = students.stream()
                    .map(StudentMapper::toDTO)
                    .toList();

            mapper.writeValue(Path.of(filePath).toFile(), dtos);

        } catch (IOException e) {
            System.out.println("Error saving ");
        }
    }

    public List<Student> loadFromFile(String filePath) {
        try {
            List<StudentDTO> dtos = mapper.readValue(
                    Path.of(filePath).toFile(),
                    new com.fasterxml.jackson.core.type.TypeReference<List<StudentDTO>>() {}
            );

            return dtos.stream()
                    .map(StudentMapper::fromDTO)
                    .toList();

        } catch (IOException e) {
            System.out.println("Error reading ");
            return List.of();
        }
    }
}
