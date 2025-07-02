package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;
import java.util.List;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    Logger logger = LoggerFactory.getLogger(StudentService.class);

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student addStudent(Student student) {
        logger.info("Старт метода добавления студента");
        return studentRepository.save(student);
    }

    public Student getStudent(long id) {
        logger.info("Старт метода получения студента");
        return studentRepository.findById(id).orElse(null);
    }

    public Student updateStudent(Student updateStudent) {
        logger.info("Старт метода изменения студента");
        if (!studentRepository.existsById(updateStudent.getId())) {
            return null;
        }
        return studentRepository.save(updateStudent);
    }

    public void deleteStudent(long id) {
        logger.info("Старт метода удаления студента");
        studentRepository.deleteById(id);
    }

    public List<Student> getStudentByAge(int age) {
        logger.info("Старт метода получения списка студентов по ID");
        return studentRepository.findStudentByAge(age);
    }

    public List<Student> getAllStudent() {
        logger.info("Старт метода получения списка всех студентов");
        return studentRepository.findAll();
    }

    public List<Student> findByAgeBetween(int min, int max) {
        logger.info("Старт поиска студентов по диапазону возраста");
        return studentRepository.findByAgeBetween(min, max);
    }

    public Faculty getFaculty(long id) {
        logger.info("Старт метода получения Факультета по ID студента");
        return studentRepository.findFacultyByStudentId(id);
    }

    public Long getQuantityStudent() {
        logger.debug("Старт метода получения количества студентов");
        try {
            Long quantity = studentRepository.getQuantityStudent();
            logger.debug("Успешно получено количество студентов {}", quantity);
            return quantity;
        } catch (Exception e) {
            logger.error("Ошибка при получении количества студентов", e);
            throw e;
        }
    }

    public Long getAverageAge() {
        logger.info("Старт метода получения среднего возраста студентов");
        return studentRepository.getAverageAge();
    }

    public List<Student> getFiveLastStudent() {
        logger.info("Старт метода получения пяти последних студентов");
        List<Student> students = studentRepository.getFiveLastStudent();
        logger.info("Метод завершен. Найдено студентов {}", students.size());
        logger.debug("список студентов: {}", students);
        return students;
    }
}
