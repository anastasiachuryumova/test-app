package ru.test.util;

import ru.test.dto.StudentDto;
import ru.test.model.Student;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class TestData {

    public static StudentDto generateStudentDto() {
        return new StudentDto(
                "John",
                "Doe",
                "Christian",
                "BI89",
                3.79);
    }

    public static Student generateStudent(){
        return new Student(
                UUID.fromString("123e4567-e89b-12d3-a456-426614174002"),
                "John",
                "Doe",
                "Christian",
                "BI89",
                3.79);
    }
}
