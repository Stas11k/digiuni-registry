package ua.edu.ukma.io;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import ua.edu.ukma.domain.Teacher;
import ua.edu.ukma.dto.TeacherDTO;
import ua.edu.ukma.converter.TeacherMapper;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class TeacherFileService {

    private final ObjectMapper mapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .enable(SerializationFeature.INDENT_OUTPUT);

    public void saveToFile(List<Teacher> teachers, String filePath) {
        try {
            List<TeacherDTO> dtos = teachers.stream()
                    .map(TeacherMapper::toDTO)
                    .toList();

            mapper.writeValue(Path.of(filePath).toFile(), dtos);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Teacher> loadFromFile(String filePath) {
        try {
            List<TeacherDTO> dtos = mapper.readValue(
                    Path.of(filePath).toFile(),
                    new TypeReference<List<TeacherDTO>>() {}
            );

            return dtos.stream()
                    .map(TeacherMapper::fromDTO)
                    .toList();

        } catch (IOException e) {
            System.out.println("Error reading ");
            return List.of();
        }
    }
}
