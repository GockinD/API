package ru.hogwarts.school.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("student")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("{id}")
    public ResponseEntity<Student> getStudent(@PathVariable long id) {
        Student student = studentService.getStudent(id);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }

    @GetMapping("/age-between")
    public ResponseEntity<List<Student>> findByAgeBetween(
            @RequestParam int min,
            @RequestParam int max) {
        return ResponseEntity.ok(studentService.findByAgeBetween(min, max));
    }

    @GetMapping("/by-age")
    public ResponseEntity<List<Student>> getStudentWithAge(@RequestParam(required = false) int age) {
        if (age > 0) {
            return ResponseEntity.ok(studentService.getStudentByAge(age));
        }
        return ResponseEntity.ok(Collections.emptyList());
    }

    @GetMapping("/all")
    public ResponseEntity<List<Student>> getAllStudent() {
        return ResponseEntity.ok(studentService.getAllStudent());
    }

    @GetMapping("/{id}/faculty")
    public ResponseEntity<Faculty> getFaculty(@PathVariable Long id) {
        Faculty faculty = studentService.getFaculty(id);
        if (faculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculty);
    }

    @PostMapping
    public ResponseEntity<Student> addStudent(@RequestBody Student student) {
        return ResponseEntity.ok(studentService.addStudent(student));
    }

    @PutMapping
    public ResponseEntity<Student> updateStudent(@RequestBody Student student) {
        Student foundStudent = studentService.updateStudent(student);
        if (foundStudent == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(foundStudent);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/quantity")
    public ResponseEntity<Long> getQuantityStudent() {
        return ResponseEntity.ok(studentService.getQuantityStudent());
    }

    @GetMapping("/averageAge")
    public ResponseEntity<Long> getAverage() {
        return ResponseEntity.ok(studentService.getAverageAge());
    }

    @GetMapping("/lostFive")
    public ResponseEntity<List<Student>> getFiveLastStudent() {
        return ResponseEntity.ok(studentService.getFiveLastStudent());
    }

    @GetMapping("/name_beginning_A")
    public List<String> getAllStudentNameBeginningWithG() {
        return studentService.getAllStudent().parallelStream()
                .map(Student::getName)
                .filter(name -> name.startsWith("Г"))
                .map(String::toUpperCase)
                .sorted()
                .toList();
    }

    @GetMapping("/average_age_all_student")
    public Double getAverageAgeStudent() {
        return studentService.getAllStudent().parallelStream()
                .mapToInt(Student::getAge)
                .average()
                .orElse(0.0);
    }

    @GetMapping("/get_time_1")
    public Long getTime1() {
        return studentService.getTime1();
    }

    @GetMapping("/get_time_2")
    public Long getTime2() {
        return studentService.getTime1();
    }

    @GetMapping("/students/print-parallel")
    public void getFirstTwoStudentParallel() {
        List<Student> students = studentService.getAllStudent();

        System.out.println(students.get(0).getName());
        System.out.println(students.get(1).getName());

        new Thread(() -> {
            System.out.println(students.get(2).getName());
            System.out.println(students.get(3).getName());
        }).start();

        new Thread(() -> {
            System.out.println(students.get(4).getName());
            System.out.println(students.get(5).getName());
        }).start();
    }

    @GetMapping("/students/print-synchronized")
    public void getFirstTwoStudentSynchronized() {
        List<Student> students = studentService.getAllStudent();
        Object flag = new Object();

        new Thread(() -> {
            synchronized (flag) {
                System.out.println(students.get(2).getName());
                System.out.println(students.get(3).getName());
            }
        }).start();

        new Thread(() -> {
            synchronized (flag) {
                System.out.println(students.get(4).getName());
                System.out.println(students.get(5).getName());
            }
        }).start();
        
        System.out.println(students.get(0).getName());
        System.out.println(students.get(1).getName());
    }
}
