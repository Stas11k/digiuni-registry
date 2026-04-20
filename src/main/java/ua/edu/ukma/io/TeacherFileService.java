package ua.edu.ukma.io;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import ua.edu.ukma.converter.TeacherMapper;
import ua.edu.ukma.domain.Teacher;
import ua.edu.ukma.dto.TeacherDTO;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class TeacherFileService {

    private final ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

    public void saveToFile(List<Teacher> teachers, String filePath) {
        try {
            List<TeacherDTO> dtos = teachers.stream().map(TeacherMapper::toDTO).toList();
            mapper.writeValue(Path.of(filePath).toFile(), dtos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<TeacherDTO> loadDTOs(String filePath) {
        try {
            return mapper.readValue(Path.of(filePath).toFile(), new TypeReference<List<TeacherDTO>>() {});
        } catch (IOException e) {
            return List.of();
        }
    }
}