package ru.test.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import ru.test.dto.StudentDto;
import ru.test.model.Student;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-14T16:52:21+0300",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.0.jar, environment: Java 17.0.8.1 (Amazon.com Inc.)"
)
@Component
public class StudentMapperImpl implements StudentMapper {

    @Override
    public Student toEntity(StudentDto studentDto) {
        if ( studentDto == null ) {
            return null;
        }

        Student.StudentBuilder student = Student.builder();

        student.name( studentDto.getName() );
        student.surname( studentDto.getSurname() );
        student.secondName( studentDto.getSecondName() );
        student.group( studentDto.getGroup() );
        student.averageMark( studentDto.getAverageMark() );

        return student.build();
    }
}
