package ru.test.service;

import ru.test.dto.StudentDto;
import ru.test.model.Student;

import java.util.List;
import java.util.UUID;

public interface AppService {

    List<Student> getStudents(UUID userId);

    Student changeStudent(UUID userId, UUID studentId, StudentDto studentDto);

    Student createStudent(UUID userId, StudentDto studentDto);

    UUID deleteStudent(UUID userId, UUID studentId);
}
