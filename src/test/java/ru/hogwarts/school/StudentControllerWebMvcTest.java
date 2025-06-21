package ru.hogwarts.school;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.FacultyService;
import ru.hogwarts.school.service.StudentService;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@ExtendWith(MockitoExtension.class)
public class StudentControllerWebMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private StudentService studentService;

    @InjectMocks
    private StudentController studentController;

    @Test
    public void addStudentTest() throws Exception {
        Long id = 1L;
        String name = "testName";
        int age = 15;
        Long facultyId = 1L;

        JSONObject studentObject = new JSONObject();
        studentObject.put("name", name);
        studentObject.put("age", age);
        studentObject.put("faculty", facultyId);

        Faculty faculty = new Faculty("testNameFaculty", "testColor");
        faculty.setId(facultyId);

        Student student = new Student();
        student.setId(id);
        student.setAge(age);
        student.setName(name);
        student.setFaculty(faculty);

        when(studentService.addStudent(any(Student.class))).thenReturn(student);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/student")
                        .content(studentObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age))
                .andExpect(jsonPath("faculty.name").value("testNameFaculty"));
    }
}
