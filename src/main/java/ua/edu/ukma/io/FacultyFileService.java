package ua.edu.ukma.io;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.edu.ukma.converter.FacultyMapper;
import ua.edu.ukma.domain.Faculty;
import ua.edu.ukma.dto.FacultyDTO;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class FacultyFileService {
    private static final Logger logger = LoggerFactory.getLogger(FacultyFileService.class);

    private final ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

    public void saveToFile(List<Faculty> faculties, String filePath) {
        try {
            List<FacultyDTO> dtos = faculties.stream().map(FacultyMapper::toDTO).toList();
            mapper.writeValue(Path.of(filePath).toFile(), dtos);
            logger.info("Saved {} faculties to {}", faculties.size(), filePath);
        } catch (IOException e) {
            logger.error("Failed to save faculties to {}", filePath, e);
        }
    }

    public List<FacultyDTO> loadDTOs(String filePath) {
        try {
            List<FacultyDTO> result = mapper.readValue(Path.of(filePath).toFile(), new TypeReference<List<FacultyDTO>>() {});
            logger.info("Loaded {} faculties from {}", result.size(), filePath);
            return result;
        } catch (IOException e) {
            logger.warn("Could not load faculties from {}. Returning empty list.", filePath);
            return List.of();
        }
    }
}