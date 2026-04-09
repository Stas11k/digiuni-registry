package ua.edu.ukma.io;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import ua.edu.ukma.converter.StudentMapper;
import ua.edu.ukma.domain.Student;
import ua.edu.ukma.dto.StudentDTO;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class StudentFileService {

    private final ObjectMapper mapper = new ObjectMapper()
            .enable(SerializationFeature.INDENT_OUTPUT);

    public boolean saveToFile(List<Student> students, String filePath) {
        try {
            List<StudentDTO> dtos = students.stream()
                    .map(StudentMapper::toDTO)
                    .toList();

            mapper.writeValue(new File(filePath), dtos);
            return true;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Student> loadFromFile(String filePath) {
        try {
            StudentDTO[] dtos = mapper.readValue(
                    new File(filePath),
                    StudentDTO[].class
            );

            return java.util.Arrays.stream(dtos)
                    .map(StudentMapper::fromDTO)
                    .toList();

        } catch (IOException e) {
            System.out.println("File not found, starting empty");
            return List.of();
        }
    }
}
