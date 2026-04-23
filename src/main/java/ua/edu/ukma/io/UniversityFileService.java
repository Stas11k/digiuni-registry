package ua.edu.ukma.io;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.edu.ukma.converter.UniversityMapper;
import ua.edu.ukma.domain.University;
import ua.edu.ukma.dto.UniversityDTO;

import java.io.IOException;
import java.nio.file.Path;

public class UniversityFileService {
    private static final Logger logger = LoggerFactory.getLogger(UniversityFileService.class);

    private final ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

    public void saveToFile(University university, String filePath) {
        try {
            UniversityDTO dto = UniversityMapper.toDTO(university);
            mapper.writeValue(Path.of(filePath).toFile(), dto);
            logger.info("University data saved to {}", filePath);
        } catch (IOException e) {
            logger.error("Error saving university data to {}", filePath, e);
        }
    }

    public University loadFromFile(String filePath) {
        try {
            UniversityDTO dto = mapper.readValue(Path.of(filePath).toFile(), UniversityDTO.class);
            logger.info("University data loaded from {}", filePath);
            return UniversityMapper.fromDTO(dto);
        } catch (IOException e) {
            logger.error("Error reading university data from {}", filePath, e);
            return null;
        }
    }
}
