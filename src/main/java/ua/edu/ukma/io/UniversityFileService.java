package ua.edu.ukma.io;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import ua.edu.ukma.domain.University;
import ua.edu.ukma.dto.UniversityDTO;
import ua.edu.ukma.converter.UniversityMapper;

import java.io.IOException;
import java.nio.file.Path;

public class UniversityFileService {

    private final ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

    public void saveToFile(University university, String filePath) {
        try {
            UniversityDTO dto = UniversityMapper.toDTO(university);
            mapper.writeValue(Path.of(filePath).toFile(), dto);
        } catch (IOException e) {
            System.out.println("Error saving ");
        }
    }

    public University loadFromFile(String filePath) {
        try {
            UniversityDTO dto = mapper.readValue(Path.of(filePath).toFile(), UniversityDTO.class);
            return UniversityMapper.fromDTO(dto);
        } catch (IOException e) {
            System.out.println("Error reading ");
            return null;
        }
    }
}
