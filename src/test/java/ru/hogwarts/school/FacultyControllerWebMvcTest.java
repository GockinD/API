package ru.hogwarts.school;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.hogwarts.school.controller.FacultyController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;
import ru.hogwarts.school.service.FacultyService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FacultyController.class)
@Import(MockedBeansConfig.class)
public class FacultyControllerWebMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FacultyService facultyService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void getFacultyByIdTest() throws Exception{
        String name = "testName";
        String color = "testColor";
        Long facultyId = 1L;

        Faculty faculty = new Faculty(name, color);
        faculty.setId(facultyId);

        when(facultyService.getFaculty(facultyId)).thenReturn(faculty);

        mockMvc.perform(get("/faculty/" + facultyId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(facultyId))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));
    }

    @Test
    public void addFacultyTest() throws Exception {
        String name = "testName";
        String color = "testColor";
        Long facultyId = 1L;

        Faculty faculty = new Faculty(name, color);
        faculty.setId(facultyId);

        when(facultyService.addFaculty(any(Faculty.class))).thenReturn(faculty);

        Map<String, Object> facultyMap = new HashMap<>();
        facultyMap.put("id", facultyId);
        facultyMap.put("name", name);
        facultyMap.put("color", color);

        String jsonRequest = objectMapper.writeValueAsString(facultyMap);

        mockMvc.perform(post("/faculty")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(facultyId))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));
    }

    @Test
    public void updateFacultyTest() throws Exception {
        String name = "testName";
        String color = "testColor";
        Long facultyId = 1L;

        Faculty faculty = new Faculty(name, color);
        faculty.setId(facultyId);

        when(facultyService.addFaculty(any(Faculty.class))).thenReturn(faculty);

        Map<String, Object> facultyMap = new HashMap<>();
        facultyMap.put("id", facultyId);
        facultyMap.put("name", name);
        facultyMap.put("color", color);

        String jsonRequest = objectMapper.writeValueAsString(facultyMap);

        mockMvc.perform(post("/faculty")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(facultyId))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));

        String nameUpdate = "updateName";
        String colorUpdate = "updateColor";
        Faculty updateFaculty = new Faculty(nameUpdate, colorUpdate);
        updateFaculty.setId(facultyId);

        when(facultyService.updateFaculty(updateFaculty)).thenReturn(updateFaculty);

        Map<String, Object> facultyMapUp = new HashMap<>();
        facultyMapUp.put("id", facultyId);
        facultyMapUp.put("name", nameUpdate);
        facultyMapUp.put("color", colorUpdate);

        String jsonRequestUp = objectMapper.writeValueAsString(facultyMapUp);

        mockMvc.perform(put("/faculty")
                        .content(jsonRequestUp)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(facultyId))
                .andExpect(jsonPath("$.name").value(nameUpdate))
                .andExpect(jsonPath("$.color").value(colorUpdate));
    }

    @Test
    public void deleteFacultyTest() throws Exception {
        String name = "testName";
        String color = "testColor";
        Long facultyId = 1L;

        Faculty faculty = new Faculty(name, color);
        faculty.setId(facultyId);

        when(facultyService.getFaculty(any(Long.class))).thenReturn(faculty);

        mockMvc.perform(get("/faculty/"+ facultyId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(facultyId))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));

        doNothing().when(facultyService).deleteFaculty(facultyId);

        mockMvc.perform(delete("/faculty/" + facultyId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(facultyService, times(1)).deleteFaculty(facultyId);

        when(facultyService.getFaculty(facultyId)).thenReturn(null);
        mockMvc.perform(get("/faculty/" + facultyId))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getFacultyByColorTest() throws Exception{
        String name = "testName";
        String color = "testColor";
        Long facultyId = 1L;

        Faculty faculty = new Faculty(name, color);
        faculty.setId(facultyId);
        List<Faculty> faculties = List.of(faculty);

        when(facultyService.getFacultyByColor(color)).thenReturn(faculties);

        mockMvc.perform(get("/faculty/by-color")
                        .param("color", color)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(facultyId))
                .andExpect(jsonPath("$[0].name").value(name))
                .andExpect(jsonPath("$[0].color").value(color));
    }

    @Test
    public void getAllFacultyTest() throws Exception{
        String name = "testName";
        String color = "testColor";
        Long facultyId = 1L;

        String name1 = "testName1";
        String color1 = "testColor1";
        Long facultyId1 = 2L;

        Faculty faculty = new Faculty(name, color);
        Faculty faculty1 = new Faculty(name1, color1);
        faculty.setId(facultyId);
        faculty1.setId(facultyId1);

        List<Faculty> faculties = new ArrayList<>();
        faculties.add(faculty);
        faculties.add(faculty1);

        when(facultyService.getAllFaculty()).thenReturn(faculties);

        mockMvc.perform(get("/faculty/all")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(facultyId))
                .andExpect(jsonPath("$[0].name").value(name))
                .andExpect(jsonPath("$[0].color").value(color))
                .andExpect(jsonPath("$[1].id").value(facultyId1))
                .andExpect(jsonPath("$[1].name").value(name1))
                .andExpect(jsonPath("$[1].color").value(color1));
    }

    @Test
    public void getFacultyByColorOrNameTest() throws Exception{
        String name = "testName";
        String color = "testColor";
        Long facultyId = 1L;

        Faculty faculty = new Faculty(name, color);
        faculty.setId(facultyId);
        List<Faculty> faculties = List.of(faculty);

        when(facultyService.findByNameIgnoreCaseOrColorIgnoreCase(name, null)).thenReturn(faculties);

        mockMvc.perform(get("/faculty/name-or-color")
                        .param("name", name)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(facultyId))
                .andExpect(jsonPath("$[0].name").value(name))
                .andExpect(jsonPath("$[0].color").value(color));

        when(facultyService.findByNameIgnoreCaseOrColorIgnoreCase(null, color)).thenReturn(faculties);

        mockMvc.perform(get("/faculty/name-or-color")
                        .param("color", color)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(facultyId))
                .andExpect(jsonPath("$[0].name").value(name))
                .andExpect(jsonPath("$[0].color").value(color));
    }

    @Test
    public void getStudentByFacultyTest() throws Exception {

        String nameFaculty = "testNameFaculty";
        String colorFaculty = "testColor";
        Long facultyId = 1L;

        Faculty faculty = new Faculty(nameFaculty, colorFaculty);
        faculty.setId(facultyId);

        Long id = 1L;
        String name = "testName";
        int age = 15;

        Student student = new Student();
        student.setId(id);
        student.setAge(age);
        student.setName(name);
        student.setFaculty(faculty);

        List<Student> students = new ArrayList<>();
        students.add(student);

        when(facultyService.findStudentByFacultyId(facultyId)).thenReturn(students);

        mockMvc.perform(get("/faculty/" + facultyId + "/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(id))
                .andExpect(jsonPath("$[0].name").value(name))
                .andExpect(jsonPath("$[0].age").value(age));
    }
}

