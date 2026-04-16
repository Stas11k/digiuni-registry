package ua.edu.ukma.io;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import ua.edu.ukma.converter.FacultyMapper;
import ua.edu.ukma.domain.Faculty;
import ua.edu.ukma.dto.FacultyDTO;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class FacultyFileService {

    private final ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

    public void saveToFile(List<Faculty> faculties, String filePath) {
        try {
            List<FacultyDTO> dtos = faculties.stream().map(FacultyMapper::toDTO).toList();
            mapper.writeValue(Path.of(filePath).toFile(), dtos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<FacultyDTO> loadDTOs(String filePath) {
        try {
            return mapper.readValue(Path.of(filePath).toFile(), new TypeReference<List<FacultyDTO>>() {});
        } catch (IOException e) {
            return List.of();
        }
    }
}