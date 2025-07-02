-- liquibase formatted sql
-- changeset dmitriy:1
CREATE INDEX IF NOT EXISTS idx_student_name ON student (name);

-- changeset dmitriy:2
CREATE INDEX IF NOT EXISTS idx_faculty_name_color ON faculty (name, color);