package ru.hogwarts.school;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StudentController.class)
@Import(MockedBeansConfig.class)
public class StudentControllerWebMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StudentService studentService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void addStudentTest() throws Exception {
        Long id = 1L;
        String name = "testName";
        int age = 15;
        Long facultyId = 1L;

        Faculty faculty = new Faculty("testNameFaculty", "testColor");
        faculty.setId(facultyId);

        Student student = new Student();
        student.setId(id);
        student.setAge(age);
        student.setName(name);
        student.setFaculty(faculty);

        when(studentService.addStudent(any(Student.class))).thenReturn(student);

        Map<String, Object> facultyMap = new HashMap<>();
        facultyMap.put("id", facultyId);
        facultyMap.put("name", "testNameFaculty");
        facultyMap.put("color", "testColor");

        Map<String, Object> studentMap = new HashMap<>();
        studentMap.put("name", name);
        studentMap.put("age", age);
        studentMap.put("faculty", facultyMap);

        String jsonRequest = objectMapper.writeValueAsString(studentMap);

        mockMvc.perform(post("/student")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age));
    }

    @Test
    public void getStudentByIdTest() throws Exception {
        Long id = 1L;
        String name = "testName";
        int age = 15;
        Long facultyId = 1L;

        Faculty faculty = new Faculty("testNameFaculty", "testColor");
        faculty.setId(facultyId);

        Student student = new Student();
        student.setId(id);
        student.setAge(age);
        student.setName(name);
        student.setFaculty(faculty);

        when(studentService.getStudent(any(Long.class))).thenReturn(student);

        Map<String, Object> facultyMap = new HashMap<>();
        facultyMap.put("id", facultyId);
        facultyMap.put("name", "testNameFaculty");
        facultyMap.put("color", "testColor");

        Map<String, Object> studentMap = new HashMap<>();
        studentMap.put("name", name);
        studentMap.put("age", age);
        studentMap.put("faculty", facultyMap);

        String jsonRequest = objectMapper.writeValueAsString(studentMap);

        mockMvc.perform(get("/student/"+ id)
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age));
    }

    @Test
    public void getStudentAgeBetweenTest() throws Exception {
        Long id = 1L;
        String name = "testName";
        int age = 15;
        Long facultyId = 1L;

        Faculty faculty = new Faculty("testNameFaculty", "testColor");
        faculty.setId(facultyId);

        Student student = new Student();
        student.setId(id);
        student.setAge(age);
        student.setName(name);
        student.setFaculty(faculty);

        List<Student> listStudent = new ArrayList<>();
        listStudent.add(student);

        when(studentService.findByAgeBetween(any(Integer.class), any(Integer.class))).thenReturn(listStudent);

        mockMvc.perform(get("/student/age-between?min=" + (age-1) + "&max="+ (age+1))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(id))
                .andExpect(jsonPath("$[0].name").value(name))
                .andExpect(jsonPath("$[0].age").value(age));
    }

    @Test
    public void getStudentByAgeTest() throws Exception {
        Long id = 1L;
        String name = "testName";
        int age = 15;
        Long facultyId = 1L;

        Faculty faculty = new Faculty("testNameFaculty", "testColor");
        faculty.setId(facultyId);

        Student student = new Student();
        student.setId(id);
        student.setAge(age);
        student.setName(name);
        student.setFaculty(faculty);

        List<Student> listStudent = new ArrayList<>();
        listStudent.add(student);

        when(studentService.getStudentByAge(any(Integer.class))).thenReturn(listStudent);

        mockMvc.perform(get("/student/by-age?age=" + age)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(id))
                .andExpect(jsonPath("$[0].name").value(name))
                .andExpect(jsonPath("$[0].age").value(age));
    }

    @Test
    public void getAllStudentTest() throws Exception {
        Long id = 1L;
        String name = "testName";
        int age = 15;
        Long facultyId = 1L;

        Long id1 = 2L;
        String name1 = "testName1";
        int age1 = 16;

        Faculty faculty = new Faculty("testNameFaculty", "testColor");
        faculty.setId(facultyId);

        Student student = new Student();
        student.setId(id);
        student.setAge(age);
        student.setName(name);
        student.setFaculty(faculty);

        Student student1 = new Student();
        student1.setId(id1);
        student1.setAge(age1);
        student1.setName(name1);
        student1.setFaculty(faculty);

        List<Student> listStudent = new ArrayList<>();
        listStudent.add(student);
        listStudent.add(student1);

        when(studentService.getAllStudent()).thenReturn(listStudent);

        mockMvc.perform(get("/student/all")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(id))
                .andExpect(jsonPath("$[0].name").value(name))
                .andExpect(jsonPath("$[0].age").value(age))
                .andExpect(jsonPath("$[1].id").value(id1))
                .andExpect(jsonPath("$[1].name").value(name1))
                .andExpect(jsonPath("$[1].age").value(age1));
    }

    @Test
    public void getFacultyByStudentIdTest() throws Exception {
        Long id = 1L;
        Long facultyId = 1L;
        Faculty faculty = new Faculty("testNameFaculty", "testColor");
        faculty.setId(facultyId);

        when(studentService.getFaculty(id)).thenReturn(faculty);

        mockMvc.perform(get("/student/" + id + "/faculty")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(facultyId))
                .andExpect(jsonPath("$.name").value("testNameFaculty"))
                .andExpect(jsonPath("$.color").value("testColor"));
    }

    @Test
    public void updateStudentTest() throws Exception {
        Long id = 1L;
        String name = "testName";
        int age = 15;
        Long facultyId = 1L;

        Faculty faculty = new Faculty("testNameFaculty", "testColor");
        faculty.setId(facultyId);

        Student student = new Student();
        student.setId(id);
        student.setAge(age);
        student.setName(name);
        student.setFaculty(faculty);

        when(studentService.getStudent(any(Long.class))).thenReturn(student);

        mockMvc.perform(get("/student/"+ id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age));

        String nameUpdate = "updateName";
        int ageUpdate = 17;
        student.setName(nameUpdate);
        student.setAge(ageUpdate);

        mockMvc.perform(get("/student/"+ id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(nameUpdate))
                .andExpect(jsonPath("$.age").value(ageUpdate));
    }

    @Test
    public void deleteStudent() throws Exception {
        Long id = 1L;
        String name = "testName";
        int age = 15;
        Long facultyId = 1L;

        Faculty faculty = new Faculty("testNameFaculty", "testColor");
        faculty.setId(facultyId);

        Student student = new Student();
        student.setId(id);
        student.setAge(age);
        student.setName(name);
        student.setFaculty(faculty);

        when(studentService.getStudent(any(Long.class))).thenReturn(student);

        mockMvc.perform(get("/student/"+ id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age));

        doNothing().when(studentService).deleteStudent(id);

        mockMvc.perform(delete("/student/" + id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(studentService, times(1)).deleteStudent(id);

        when(studentService.getStudent(id)).thenReturn(null);
        mockMvc.perform(get("/student/" + id))
                .andExpect(status().isNotFound());
    }
}