package ua.edu.ukma.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AsyncSaveService {

    private static final Logger logger = LoggerFactory.getLogger(AsyncSaveService.class);
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final DataSaveService dataSaveService = new DataSaveService();

    public void saveAsync(DataContext context) {
        executor.submit(() -> {
            try {
                logger.info("Starting async save");
                dataSaveService.saveAll(
                        context.facultyRepo(),
                        context.departmentRepo(),
                        context.specialtyRepo(),
                        context.teacherRepo(),
                        context.studentRepo(),
                        context.university()
                );
                logger.info("Async save completed successfully");
            } catch (Exception e) {
                logger.error("Async save failed", e);
            }
        });
    }
}