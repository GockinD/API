SELECT student.name, student.age, faculty.name FROM Student LEFT JOIN Faculty ON student.faculty_id = faculty.id

SELECT student.name, avatar.id  FROM Student INNER JOIN Avatar ON student.avatar_id = avatar.id