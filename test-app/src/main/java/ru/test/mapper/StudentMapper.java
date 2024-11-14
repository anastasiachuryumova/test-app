package ru.test.mapper;

import org.mapstruct.Mapper;
import ru.test.dto.StudentDto;
import ru.test.model.Student;

@Mapper(componentModel = "spring")
public interface StudentMapper {

    Student toEntity(StudentDto studentDto);
}
