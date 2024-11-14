package ru.test.mapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.test.dto.StudentDto;
import ru.test.model.Student;
import ru.test.util.TestData;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class StudentMapperTest {

    @InjectMocks
    private StudentMapperImpl studentMapper;

    private final StudentDto studentDto = TestData.generateStudentDto();

    @Test
    void toEntity() {

        Student result = studentMapper.toEntity(studentDto);

        assertNotNull(result);
        assertEquals(studentDto.getName(), result.getName());
        assertEquals(studentDto.getSecondName(), result.getSecondName());
        assertEquals(studentDto.getSurname(), result.getSurname());
        assertEquals(studentDto.getGroup(), result.getGroup());
        assertEquals(studentDto.getAverageMark(), result.getAverageMark());
    }
}
