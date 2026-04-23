package ua.edu.ukma.io;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.edu.ukma.converter.DepartmentMapper;
import ua.edu.ukma.domain.Department;
import ua.edu.ukma.dto.DepartmentDTO;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class DepartmentFileService {
    private static final Logger logger = LoggerFactory.getLogger(DepartmentFileService.class);

    private final ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

    public void saveToFile(List<Department> departments, String filePath) {
        try {
            List<DepartmentDTO> dtos = departments.stream().map(DepartmentMapper::toDTO).toList();
            mapper.writeValue(Path.of(filePath).toFile(), dtos);
            logger.info("Saved {} departments to {}", departments.size(), filePath);
        } catch (IOException e) {
            logger.error("Failed to save departments to {}", filePath, e);
        }
    }

    public List<DepartmentDTO> loadDTOs(String filePath) {
        try {
            List<DepartmentDTO> result = mapper.readValue(Path.of(filePath).toFile(), new TypeReference<List<DepartmentDTO>>() {});
            logger.info("Loaded {} departments from {}", result.size(), filePath);
            return result;
        } catch (IOException e) {
            logger.warn("Could not load departments from {}. Returning empty list.", filePath);
            return List.of();
        }
    }
}