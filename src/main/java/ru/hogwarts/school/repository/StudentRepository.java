package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.List;
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findStudentByAge(long age);

    List<Student> findByAgeBetween(int min, int max);

    @Query("SELECT s.faculty FROM Student s WHERE s.id = :id")
    Faculty findFacultyByStudentId(@Param("id") long id);

    @Query(value = "SELECT COUNT(DISTINCT id) FROM Student", nativeQuery = true)
    Long getQuantityStudent();

    @Query(value = "SELECT ROUND(AVG(age)) FROM Student", nativeQuery = true)
    Long getAverageAge();

    @Query(value = "SELECT * FROM Student ORDER BY id DESC LIMIT 5", nativeQuery = true)
    List<Student> getFiveLastStudent();

}
