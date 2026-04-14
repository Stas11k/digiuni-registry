package ua.edu.ukma.io;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import ua.edu.ukma.converter.StudentMapper;
import ua.edu.ukma.domain.Specialty;
import ua.edu.ukma.domain.Student;
import ua.edu.ukma.dto.StudentDTO;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StudentFileService {

    private final ObjectMapper mapper = new ObjectMapper()
            .enable(SerializationFeature.INDENT_OUTPUT);

    public boolean saveToFile(List<Student> students, String filePath) {
        try {
            List<StudentDTO> dtos = students.stream()
                    .map(StudentMapper::toDTO)
                    .toList();

            mapper.writeValue(new File(filePath), dtos);
            return true;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Student> loadFromFile(String filePath, List<Specialty> specialties) {
        try {
            StudentDTO[] dtos = mapper.readValue(
                    new File(filePath),
                    StudentDTO[].class
            );

            List<Student> result = new ArrayList<>();

            for (StudentDTO dto : dtos) {
                Specialty specialty = findSpecialtyByName(dto.specialtyName(), specialties);

                if (specialty != null) {
                    result.add(StudentMapper.fromDTO(dto, specialty));
                }
            }

            return result;

        } catch (IOException e) {
            System.out.println("File not found, starting empty");
            return List.of();
        }
    }

    private Specialty findSpecialtyByName(String specialtyName, List<Specialty> specialties) {
        if (specialtyName == null) return null;

        for (Specialty specialty : specialties) {
            if (specialty.getName().equalsIgnoreCase(specialtyName)) {
                return specialty;
            }
        }
        return null;
    }
}