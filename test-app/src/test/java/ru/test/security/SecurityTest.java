package ru.test.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.test.config.TestConfig;
import ru.test.config.SecurityConfig;
import java.util.UUID;

@SpringBootTest(classes = {TestConfig.class, SecurityConfig.class})
@AutoConfigureMockMvc
public class SecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void responseStatusIsUnauthorizedWhenTokenIsInvalid() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/test")
                        .header("Authorization", "Bearer fafsdfsdfDFDafd")
        ).andExpectAll(
                MockMvcResultMatchers.status().isUnauthorized()
        );
    }

    @Test
    public void responseStatusIsForbiddenWhenNotAuthHeaderPresent() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/test")
        ).andExpectAll(
                MockMvcResultMatchers.status().isForbidden()
        );
    }

    @Test
    public void responseStatusIsUnauthorizedWhenUserIdIsNotPresent() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/test")
                        .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJVc2VyIGRldGFpbHMiLCJpc3MiOiJBU1RMSU5LIn0._K5DyFe0yAsSH2eSsP5bIGDJ7U1u5PrJeOfUFKdtQ6U")
        ).andExpectAll(
                MockMvcResultMatchers.status().isUnauthorized()
        );
    }

    @Test
    public void responseStatusIsOk() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/test")
                        .with(SecurityMockMvcRequestPostProcessors.user(UUID.randomUUID().toString()))
        ).andExpectAll(
                MockMvcResultMatchers.status().isOk()
        );
    }
}
