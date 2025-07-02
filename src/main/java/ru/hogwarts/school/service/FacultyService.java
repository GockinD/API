package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.*;

@Service
public class FacultyService {
    Logger logger = LoggerFactory.getLogger(FacultyService.class);

    private final FacultyRepository facultyRepository;


    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty addFaculty(Faculty faculty) {
        logger.info("Старт метода добавления факультета");
        return facultyRepository.save(faculty);
    }

    public Faculty getFaculty(long id) {
        logger.info("Старт метода получения факультета");
        return facultyRepository.findById(id).orElse(null);
    }

    public Faculty updateFaculty(Faculty updatedFaculty) {
        logger.info("Старт метода изменения факультета");
        if (!facultyRepository.existsById(updatedFaculty.getId())) {
            return null;
        }
        return facultyRepository.save(updatedFaculty);
    }

    public void deleteFaculty(long id) {
        logger.info("Старт метода удаления факультета");
        facultyRepository.deleteById(id);
    }

    public List<Faculty> getFacultyByColor(String color) {
        logger.info("Старт метода получения факультета по цвету");
        try {
            List<Faculty> faculties = facultyRepository.findByColorIgnoreCase(color);
            logger.debug("Успешно получен факультет по цвету");
            return faculties;
        } catch (Exception e) {
            logger.error("Ошибка при получении факультета по цвету", e);
            throw e;
        }
    }

    public List<Faculty> getAllFaculty() {
        logger.info("Старт метода получения всех факультетов");
        return facultyRepository.findAll();
    }

    public List<Faculty> findByNameIgnoreCaseOrColorIgnoreCase(String name, String color) {
        logger.info("Старт метода получения факультета по цвету или имени");
        return facultyRepository.findByNameIgnoreCaseOrColorIgnoreCase(name, color);
    }

    public List<Student> findStudentByFacultyId(Long facultyId) {
        logger.info("Старт метода получения студентов по факультету");
        return facultyRepository.findStudentsByFacultyId(facultyId);
    }
}
