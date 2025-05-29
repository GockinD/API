package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student addStudent(Student student) {
        return studentRepository.save(student);
    }

    public Student getStudent(long id) {
        return studentRepository.findById(id).orElse(null);
    }

    public Student updateStudent(Student updateStudent) {
        if (!studentRepository.existsById(updateStudent.getId())) {
            return null;
        }
        return studentRepository.save(updateStudent);
    }

    public void deleteStudent(long id) {
        studentRepository.deleteById(id);
    }

    public Collection<Student> getStudentByAge(int age) {
        return studentRepository.findStudentByAge(age);
    }

    public Collection<Student> getAllStudent() {
        return studentRepository.findAll();
    }

    public Collection<Student> findByAgeBetween(int min, int max) {
        return studentRepository.findByAgeBetween(min, max);
    }

    public Faculty getFaculty(String name) {
        return studentRepository.findFacultyByStudentName(name);
    }
}
