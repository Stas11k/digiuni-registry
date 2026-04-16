package ua.edu.ukma.io;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import ua.edu.ukma.converter.SpecialtyMapper;
import ua.edu.ukma.domain.Specialty;
import ua.edu.ukma.dto.SpecialtyDTO;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class SpecialtyFileService {

    private final ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

    public void saveToFile(List<Specialty> specialties, String filePath) {
        try {
            List<SpecialtyDTO> dtos = specialties.stream().map(SpecialtyMapper::toDTO).toList();
            mapper.writeValue(Path.of(filePath).toFile(), dtos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<SpecialtyDTO> loadDTOs(String filePath) {
        try {
            return mapper.readValue(Path.of(filePath).toFile(), new TypeReference<List<SpecialtyDTO>>() {});
        } catch (IOException e) {
            return List.of();
        }
    }
}