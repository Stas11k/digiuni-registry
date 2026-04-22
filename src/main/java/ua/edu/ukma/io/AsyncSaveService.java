package ua.edu.ukma.io;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AsyncSaveService {

    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final DataSaveService dataSaveService = new DataSaveService();

    public void saveAsync(DataContext context) {
        executor.submit(() -> {
            try {
                dataSaveService.saveAll(
                        context.facultyRepo(),
                        context.departmentRepo(),
                        context.specialtyRepo(),
                        context.teacherRepo(),
                        context.studentRepo(),
                        context.university()
                );
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
