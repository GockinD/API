package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StudentService {
    private final Map<Long, Student> studentMap = new HashMap<>();
    private long count = 0;

    public Student addStudent(Student student) {
        student.setId(count++);
        studentMap.put(student.getId(), student);
        return student;
    }

    public Student getStudent(long id) {
        return studentMap.get(id);
    }

    public Student updateStudent(Student updateStudent) {
        if (!studentMap.containsKey(updateStudent.getId())) {
            return null;
        }
        studentMap.put(updateStudent.getId(), updateStudent);
        return updateStudent;
    }

    public boolean deleteStudent(long id) {
        return studentMap.remove(id) != null;
    }

    public Collection<Student> getStudentByAge(int age) {
        return studentMap.values().stream()
                .filter(student -> student.getAge() == age)
                .toList();
    }
}
