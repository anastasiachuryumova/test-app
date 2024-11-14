package ru.test.repo;

import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import ru.test.model.Student;
import ru.test.util.ContainerInitializer;
import ru.test.util.TestData;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@DirtiesContext
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = "/populate.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class StudentRepositoryTest extends ContainerInitializer {

    @Autowired
    private StudentRepository studentRepository;

    private final Student student = TestData.generateStudent();

    @Test
    void findByNameAndSecondNameAndSurnameSuccess() {

        var actualResult = studentRepository
                .findByNameAndSecondNameAndSurname(student.getName(), student.getSecondName(), student.getSurname());

        assertThat(actualResult).isPresent();
        var expectedId = UUID.fromString("123e4567-e89b-12d3-a456-426614174002");
        assertThat(actualResult.get().getId()).isEqualTo(expectedId);
    }

    @Test
    void findAll() {


        Iterable<Student> result = studentRepository.findAll();

        AssertionsForClassTypes.assertThat(result.iterator().next().getName()).isEqualTo(student.getName());
        AssertionsForClassTypes.assertThat(result.iterator().next().getSecondName()).isEqualTo(student.getSecondName());
        AssertionsForClassTypes.assertThat(result.iterator().next().getSurname()).isEqualTo(student.getSurname());
        AssertionsForClassTypes.assertThat(result.iterator().next().getGroup()).isEqualTo(student.getGroup());
        AssertionsForClassTypes.assertThat(result.iterator().next().getAverageMark()).isEqualTo(student.getAverageMark());
        AssertionsForClassTypes.assertThat(result.iterator().next().getId()).isEqualTo(student.getId());
    }
}