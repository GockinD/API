package ru.hogwarts.school;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.controller.FacultyController;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.Collection;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private StudentController studentController;

    @Autowired
    private FacultyController facultyController;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void getStudentByIdTest() throws Exception {
        Assertions.assertThat(this.testRestTemplate.getForObject("http://localhost:" + port + "/student", Student.class))
                .isNotNull();
    }

    @Test
    public void getStudentWithAgeBetweenTest() throws Exception {
        Assertions.assertThat(this.testRestTemplate.getForObject("http://localhost:" + port + "/student/age-between", Student.class))
                .isNotNull();
    }

    @Test
    public void getStudentByAgeTest() throws Exception {
        Assertions.assertThat(this.testRestTemplate.getForObject("http://localhost:" + port + "/student/by-age", Student.class))
                .isNotNull();
    }

    @Test
    public void getAllStudentTest() throws Exception {
        Assertions.assertThat(this.testRestTemplate.getForObject("http://localhost:" + port + "/student/all", Collection.class))
                .isNotNull();
    }

    @Test
    public void getFacultyByStudentTest() throws Exception {
        int id = 2;
        Assertions.assertThat(this.testRestTemplate.getForObject("http://localhost:" + port + "/student/" + id + "/faculty", Faculty.class))
                .isNotNull();
    }

    @Test
    public void postStudent()throws Exception {
        Student student = new Student();
        student.setId(1L);
        student.setAge(12);
        student.setFaculty(new Faculty("testNameFaculty", "testColor"));
        student.setName("testName");

        Assertions.assertThat(this.testRestTemplate.postForObject("http://localhost:" + port + "/student", student, String.class))
                .isNotNull();
    }

    @Test
    public void putStudentTest()throws Exception {
        Faculty faculty = new Faculty("testNameFaculty", "testColor");
        facultyController.addFaculty(faculty);

        Student student = new Student();
        student.setId(1L);
        student.setAge(12);
        student.setFaculty(faculty);
        student.setName("testName");
        studentController.addStudent(student);

        Student putStudent = new Student();
        putStudent.setId(student.getId());
        putStudent.setName("updateName");
        putStudent.setAge(12);
        putStudent.setFaculty(faculty);

        ResponseEntity<Void> response = this.testRestTemplate.exchange(
                "http://localhost:" + port + "/student",
                HttpMethod.PUT,
                new HttpEntity<>(putStudent),
                Void.class
        );
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void deleteStudentTest() throws Exception {
        Faculty faculty = new Faculty("testNameFaculty", "testColor");
        faculty = facultyController.addFaculty(faculty);

        Student student = new Student();
        student.setId(11L);
        student.setName("Test Student");
        student.setAge(20);
        student.setFaculty(faculty);

        ResponseEntity<Void> response = this.testRestTemplate.exchange(
                "http://localhost:" + port + "/faculty/" + faculty.getId(),
                HttpMethod.DELETE,
                null,
                Void.class);

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
