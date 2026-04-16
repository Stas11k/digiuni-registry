package ua.edu.ukma.io;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import ua.edu.ukma.converter.DepartmentMapper;
import ua.edu.ukma.domain.Department;
import ua.edu.ukma.dto.DepartmentDTO;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class DepartmentFileService {

    private final ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

    public void saveToFile(List<Department> departments, String filePath) {
        try {List<DepartmentDTO> dtos = departments.stream().map(DepartmentMapper::toDTO).toList();
            mapper.writeValue(Path.of(filePath).toFile(), dtos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<DepartmentDTO> loadDTOs(String filePath) {
        try {
            return mapper.readValue(Path.of(filePath).toFile(), new TypeReference<List<DepartmentDTO>>() {});
        } catch (IOException e) {
            return List.of();
        }
    }
}