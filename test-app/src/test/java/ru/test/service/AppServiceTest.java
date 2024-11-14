package ru.test.service;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.test.dto.StudentDto;
import ru.test.mapper.StudentMapper;
import ru.test.model.Student;
import ru.test.repo.StudentRepository;
import ru.test.service.impl.AppServiceImpl;
import ru.test.util.TestData;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.shouldHaveThrown;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AppServiceTest {
    @Mock
    private StudentRepository studentRepository;
    @Mock
    private StudentMapper studentMapper;
    @Captor
    private ArgumentCaptor<Student> studentArgumentCaptor;

    @InjectMocks
    private AppServiceImpl appService;

    private final UUID userId = UUID.fromString("ce8519fd-23f5-4eb3-80f9-85e5afec6b0d");
    private final UUID studentId = UUID.fromString("123e4567-e89b-12d3-a456-426614174002");
    private final StudentDto studentDto = TestData.generateStudentDto();
    private final Student student = TestData.generateStudent();
    private final List<Student> students = List.of(student);

    @Test
    void createStudentSuccess() {

        when(studentRepository.findByNameAndSecondNameAndSurname(any(), any(), any())).thenReturn(Optional.of(student));
        when(studentMapper.toEntity(studentDto)).thenReturn(student);
        when(studentRepository.save(any())).thenReturn(student);

        var actualResult = appService.createStudent(userId, studentDto);
        assertThat(actualResult).isEqualTo(student);
        verify(studentMapper, times(1)).toEntity(any());
        verify(studentRepository, times(1)).save(studentArgumentCaptor.capture());
        var studentArgumentCaptorValue = studentArgumentCaptor.getValue();
        assertThat(studentArgumentCaptorValue).isNotNull();
        assertThat(studentArgumentCaptorValue).isEqualTo(student);
    }

    @Test
    void createStudentThrowEntityExists() {

        when(studentRepository.findByNameAndSecondNameAndSurname(any(), any(), any())).thenThrow(new EntityExistsException(""));
        assertThatExceptionOfType(EntityExistsException.class)
                .isThrownBy(() -> {
                    appService.createStudent(userId, studentDto);
                }).withMessage("");
    }

    @Test
    void getStudentsSuccess() {

        when(studentRepository.findAll()).thenReturn(students);

        var actualResult = appService.getStudents(userId);
        assertThat(actualResult.size()).isEqualTo(1);
        verify(studentRepository, times(1)).findAll();
    }

    @Test
    void changeStudentSuccess() {

        when(studentRepository.findById(any())).thenReturn(Optional.of(student));
        when(studentRepository.save(any())).thenReturn(student);

        var actualResult = appService.changeStudent(userId, studentId, studentDto);        assertThat(actualResult.getName()).isEqualTo(student.getName());
        assertThat(actualResult.getSecondName()).isEqualTo(student.getSecondName());
        assertThat(actualResult.getSurname()).isEqualTo(student.getSurname());
        assertThat(actualResult.getGroup()).isEqualTo(student.getGroup());
        assertThat(actualResult.getAverageMark()).isEqualTo(student.getAverageMark());

        verify(studentRepository, times(1)).findById(studentId);
        verify(studentRepository, times(1)).save(studentArgumentCaptor.capture());

        var studentArgumentCaptorValue = studentArgumentCaptor.getValue();

        assertThat(studentArgumentCaptorValue).isNotNull();
        assertThat(studentArgumentCaptorValue.getName()).isEqualTo(student.getName());
        assertThat(studentArgumentCaptorValue.getSecondName()).isEqualTo(student.getSecondName());
        assertThat(studentArgumentCaptorValue.getSurname()).isEqualTo(student.getSurname());
        assertThat(studentArgumentCaptorValue.getGroup()).isEqualTo(student.getGroup());
        assertThat(studentArgumentCaptorValue.getAverageMark()).isEqualTo(student.getAverageMark());
    }

    @Test
    void changeStudentThrowEntityNotFound() {

        when(studentRepository.findById(any())).thenThrow(new EntityNotFoundException(""));

        assertThatExceptionOfType(EntityNotFoundException.class)
                .isThrownBy(() -> {
                    appService.changeStudent(userId, studentId, studentDto);
                }).withMessage("");

        verify(studentRepository, times(1)).findById(studentId);
        verify(studentRepository, times(0)).save(student);
    }

    @Test
    void deleteStudentSuccess() {

        when(studentRepository.findById(any())).thenReturn(Optional.of(student));

        var actualResult = appService.deleteStudent(userId, studentId);
        assertThat(actualResult).isEqualTo(studentId);
        verify(studentRepository, times(1)).deleteById(any());
    }

    @Test
    void getStudentThrowEntityNotFound() {

        when(studentRepository.findById(any())).thenThrow(new EntityNotFoundException(""));

        assertThatExceptionOfType(EntityNotFoundException.class)
                .isThrownBy(() -> {
                    appService.deleteStudent(userId, studentId);
                }).withMessage("");
        verify(studentRepository, times(0)).deleteById(any());
    }
}
