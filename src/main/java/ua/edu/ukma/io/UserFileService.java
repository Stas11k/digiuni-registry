package ua.edu.ukma.io;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.edu.ukma.auth.User;
import ua.edu.ukma.converter.UserMapper;
import ua.edu.ukma.dto.UserDTO;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class UserFileService {
    private static final Logger logger = LoggerFactory.getLogger(UserFileService.class);

    private final ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

    public void saveToFile(List<User> users, String filePath) {
        try {
            Path path = Path.of(filePath);
            if (path.getParent() != null) {
                Files.createDirectories(path.getParent());
            }
            List<UserDTO> dtos = users.stream()
                    .map(UserMapper::toDTO)
                    .toList();

            mapper.writeValue(path.toFile(), dtos);
            logger.info("Saved {} users to {}", users.size(), filePath);
        } catch (IOException e) {
            logger.error("Failed to save users to {}", filePath, e);
        }
    }

    public List<UserDTO> loadDTOs(String filePath) {
        try {
            Path path = Path.of(filePath);
            if (!Files.exists(path)) {
                logger.warn("Users file {} does not exist. Returning empty list.", filePath);
                return List.of();
            }
            List<UserDTO> result = mapper.readValue(path.toFile(), new TypeReference<List<UserDTO>>() {});
            logger.info("Loaded {} users from {}", result.size(), filePath);
            return result;
        } catch (IOException e) {
            logger.error("Failed to load users from {}", filePath, e);
            return List.of();
        }
    }
}