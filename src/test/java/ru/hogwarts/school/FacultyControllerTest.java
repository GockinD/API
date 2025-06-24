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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
    public class FacultyControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private StudentController studentController;

    @Autowired
    private FacultyController facultyController;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void getFacultyByIdTest() throws Exception {
        Assertions.assertThat(this.testRestTemplate.getForObject("http://localhost:" + port + "/faculty/" + 1, Faculty.class))
                .isNotNull();
    }

    @Test
    public void postFacultyTest()throws Exception {
        Faculty faculty = new Faculty("testName","testColor");

        Assertions.assertThat(this.testRestTemplate.postForObject("http://localhost:" + port + "/faculty", faculty, Faculty.class))
                .isNotNull();
    }

    @Test
    public void updateFacultyTest() throws Exception {
        Faculty originalFaculty = new Faculty("Original Name", "Original Color");
        ResponseEntity<Faculty> createResponse = testRestTemplate.postForEntity(
                "http://localhost:" + port + "/faculty",
                originalFaculty,
                Faculty.class);
        Faculty savedFaculty = createResponse.getBody();
        assertNotNull(savedFaculty);
        assertNotNull(savedFaculty.getId());

        Faculty updatedFaculty = new Faculty();
        updatedFaculty.setId(savedFaculty.getId()); // Важно: используем тот же ID
        updatedFaculty.setName("Updated Name");
        updatedFaculty.setColor("Updated Color");

        ResponseEntity<Faculty> updateResponse = testRestTemplate.exchange(
                "http://localhost:" + port + "/faculty",
                HttpMethod.PUT,
                new HttpEntity<>(updatedFaculty),
                Faculty.class);

        assertEquals(HttpStatus.OK, updateResponse.getStatusCode());
        Faculty responseFaculty = updateResponse.getBody();
        assertNotNull(responseFaculty);

        assertEquals(updatedFaculty.getId(), responseFaculty.getId());
        assertEquals(updatedFaculty.getName(), responseFaculty.getName());
        assertEquals(updatedFaculty.getColor(), responseFaculty.getColor());
    }

    @Test
    public void deleteFacultyTest() throws Exception {
        Faculty faculty = new Faculty("testNameFaculty", "testColor");
        faculty = facultyController.addFaculty(faculty);

        ResponseEntity<Void> response = this.testRestTemplate.exchange(
                "http://localhost:" + port + "/faculty/" + faculty.getId(),
                HttpMethod.DELETE,
                null,
                Void.class);

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void getFacultyByColorTest() throws Exception {
        Faculty faculty = new Faculty("testNameFaculty", "testColor");
        faculty = facultyController.addFaculty(faculty);

        Assertions.assertThat(this.testRestTemplate.getForObject("http://localhost:" + port + "/faculty/by_color/" + faculty.getColor(), Faculty.class))
                .isNotNull();
    }

    @Test
    public void getAllFacultyTest() throws Exception {
        Assertions.assertThat(this.testRestTemplate.getForObject("http://localhost:" + port + "/faculty/all", Collection.class))
                .isNotNull();
    }

    @Test
    public void getFacultyByNameOrColorTest() throws Exception {
        Faculty faculty = new Faculty("testNameFaculty", "testColor");
        faculty = facultyController.addFaculty(faculty);

        Assertions.assertThat(this.testRestTemplate.getForObject("http://localhost:" + port + "/faculty/name-or-color/" + faculty.getName(), Faculty.class))
                .isNotNull();
        Assertions.assertThat(this.testRestTemplate.getForObject("http://localhost:" + port + "/faculty/name-or-color/" + faculty.getColor(), Faculty.class))
                .isNotNull();
    }

    @Test
    public void getStudentByFacultyId() throws Exception {
        Faculty faculty = new Faculty("testNameFaculty", "testColor");
        faculty = facultyController.addFaculty(faculty);

        Student student = new Student("nameTest", 16, faculty);

        Assertions.assertThat(this.testRestTemplate.getForObject("http://localhost:" + port + "/faculty/" + faculty.getId() + "/students", Collection.class))
                .isNotNull();
    }
}
