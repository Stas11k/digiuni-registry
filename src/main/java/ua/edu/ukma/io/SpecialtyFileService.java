package ua.edu.ukma.io;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.edu.ukma.converter.SpecialtyMapper;
import ua.edu.ukma.domain.Specialty;
import ua.edu.ukma.dto.SpecialtyDTO;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class SpecialtyFileService {
    private static final Logger logger = LoggerFactory.getLogger(SpecialtyFileService.class);

    private final ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

    public void saveToFile(List<Specialty> specialties, String filePath) {
        try {
            List<SpecialtyDTO> dtos = specialties.stream().map(SpecialtyMapper::toDTO).toList();
            mapper.writeValue(Path.of(filePath).toFile(), dtos);
            logger.info("Saved {} specialties to {}", specialties.size(), filePath);
        } catch (IOException e) {
            logger.error("Failed to save specialties to {}", filePath, e);
        }
    }

    public List<SpecialtyDTO> loadDTOs(String filePath) {
        try {
            List<SpecialtyDTO> result = mapper.readValue(Path.of(filePath).toFile(), new TypeReference<List<SpecialtyDTO>>() {});
            logger.info("Loaded {} specialties from {}", result.size(), filePath);
            return result;
        } catch (IOException e) {
            logger.warn("Could not load specialties from {}. Returning empty list.", filePath);
            return List.of();
        }
    }
}