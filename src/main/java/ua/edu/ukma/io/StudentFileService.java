package ua.edu.ukma.io;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.edu.ukma.converter.StudentMapper;
import ua.edu.ukma.domain.Student;
import ua.edu.ukma.dto.StudentDTO;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class StudentFileService {
    private static final Logger logger = LoggerFactory.getLogger(StudentFileService.class);

    private final ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

    public boolean saveToFile(List<Student> students, String filePath) {
        try {
            List<StudentDTO> dtos = students.stream().map(StudentMapper::toDTO).toList();
            mapper.writeValue(Path.of(filePath).toFile(), dtos);
            logger.info("Saved {} students to {}", students.size(), filePath);
            return true;
        } catch (IOException e) {
            logger.error("Failed to save students to {}", filePath, e);
            return false;
        }
    }

    public List<StudentDTO> loadDTOs(String filePath) {
        try {
            List<StudentDTO> result = mapper.readValue(Path.of(filePath).toFile(), new TypeReference<List<StudentDTO>>() {});
            logger.info("Loaded {} students from {}", result.size(), filePath);
            return result;
        } catch (IOException e) {
            logger.warn("Could not load students from {}. Returning empty list.", filePath);
            return List.of();
        }
    }
}