package ru.test.repo;

import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import ru.test.model.User;
import ru.test.util.ContainerInitializer;
import ru.test.util.TestData;

@DataJpaTest
@DirtiesContext
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = "/populate.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class UserRepositoryTest extends ContainerInitializer {

    @Autowired
    private UserRepository userRepository;

    private final User user = TestData.generateUser();


    @Test
    void findAll() {

        Iterable<User> result = userRepository.findAll();

        AssertionsForClassTypes.assertThat(result.iterator().next().getName()).isEqualTo(user.getName());
        AssertionsForClassTypes.assertThat(result.iterator().next().getEmail()).isEqualTo(user.getEmail());
        AssertionsForClassTypes.assertThat(result.iterator().next().getPassword()).isEqualTo(user.getPassword());
        AssertionsForClassTypes.assertThat(result.iterator().next().getUserStatus()).isEqualTo(user.getUserStatus());
        AssertionsForClassTypes.assertThat(result.iterator().next().getId()).isEqualTo(user.getId());
    }
}