--liquibase formatted sql
--changeset dmitriy:1
CREATE INDEX inx_student_name ON Student (name);

--changeset dmitriy:2
CREATE INDEX inx_by_nameFaculty_and_color ON Faculty (name, color);
