package ua.edu.ukma.io;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.edu.ukma.converter.TeacherMapper;
import ua.edu.ukma.domain.Teacher;
import ua.edu.ukma.dto.TeacherDTO;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class TeacherFileService {
    private static final Logger logger = LoggerFactory.getLogger(TeacherFileService.class);

    private final ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

    public void saveToFile(List<Teacher> teachers, String filePath) {
        try {
            List<TeacherDTO> dtos = teachers.stream().map(TeacherMapper::toDTO).toList();
            mapper.writeValue(Path.of(filePath).toFile(), dtos);
            logger.info("Saved {} teachers to {}", teachers.size(), filePath);
        } catch (IOException e) {
            logger.error("Failed to save teachers to {}", filePath, e);
        }
    }

    public List<TeacherDTO> loadDTOs(String filePath) {
        try {
            List<TeacherDTO> result = mapper.readValue(Path.of(filePath).toFile(), new TypeReference<List<TeacherDTO>>() {});
            logger.info("Loaded {} teachers from {}", result.size(), filePath);
            return result;
        } catch (IOException e) {
            logger.warn("Could not load teachers from {}. Returning empty list.", filePath);
            return List.of();
        }
    }
}