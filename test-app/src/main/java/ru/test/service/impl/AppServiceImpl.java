package ru.test.service.impl;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.test.dto.StudentDto;
import ru.test.mapper.StudentMapper;
import ru.test.model.Student;
import ru.test.repo.StudentRepository;
import ru.test.service.AppService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AppServiceImpl implements AppService {

    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;

    @Override
    public List<Student> getStudents(UUID userId) {
        return studentRepository.findAll();
    }

    @Override
    public Student changeStudent(UUID userId, UUID studentId, StudentDto studentDto) {
        Student student = studentRepository.findById(studentId).orElseThrow(EntityNotFoundException::new);
        Student newStudent = Student.builder()
                .id(student.getId())
                .name(studentDto.getName())
                .averageMark(studentDto.getAverageMark())
                .secondName(studentDto.getSecondName())
                .surname(studentDto.getSurname())
                .group(studentDto.getGroup())
                .build();
        studentRepository.deleteById(studentId);
        studentRepository.save(newStudent);
        return newStudent;
    }

    @Override
    public Student createStudent(UUID userId, StudentDto studentDto) {
        studentRepository.findByNameAndSecondNameAndSurname(
                studentDto.getName(),
                studentDto.getSecondName(),
                studentDto.getSurname())
                .orElseThrow(EntityExistsException::new);
        Student newStudent = studentMapper.toEntity(studentDto);
        studentRepository.save(newStudent);
        return newStudent;
    }

    @Override
    public UUID deleteStudent(UUID userId, UUID studentId) {
        studentRepository.findById(studentId).orElseThrow(EntityNotFoundException::new);
        studentRepository.deleteById(studentId);
        return studentId;
    }
}
