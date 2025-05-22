package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class FacultyService {
    private final Map<Long, Faculty> facultyMap = new HashMap<>();
    private long count = 0;

    public Faculty addFaculty(Faculty faculty) {
        faculty.setId(count++);
        facultyMap.put(faculty.getId(), faculty);
        return faculty;
    }

    public Faculty getFaculty(long id) {
        return facultyMap.get(id);
    }

    public Faculty updateFaculty(Faculty updatedFaculty) {
        if (!facultyMap.containsKey(updatedFaculty.getId())) {
            return null;
        }
        facultyMap.put(updatedFaculty.getId(), updatedFaculty);
        return updatedFaculty;
    }

    public boolean deleteFaculty(long id) {
        return facultyMap.remove(id) != null;
    }

    public Collection<Faculty> getFacultyByColor(String color) {
        return facultyMap.values().stream()
                .filter(faculty -> Objects.equals(faculty.getColor(), color))
                .toList();
    }
}
