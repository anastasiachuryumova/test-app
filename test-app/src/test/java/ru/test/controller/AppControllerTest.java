package ru.test.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.test.config.SecurityConfig;
import ru.test.dto.StudentDto;
import ru.test.model.Student;
import ru.test.service.AppService;
import ru.test.util.TestData;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AppController.class)
@ImportAutoConfiguration(SecurityConfig.class)
public class AppControllerTest {

    private final List<Student> students = List.of(TestData.generateStudent());
    private final List<Student> studentsEmpty = List.of();
    private final Student student = TestData.generateStudent();
    private final StudentDto studentDto = TestData.generateStudentDto();
    private final String studentId = "123e4567-e89b-12d3-a456-426614174002";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private AppService appService;

    @Test
    @WithMockUser(username = "ce8519fd-23f5-4eb3-80f9-85e5afec6b0d", roles = "USER")
    void getStudentsSuccess() throws Exception {

        when(appService.getStudents(any())).thenReturn(students);

        mockMvc.perform(get("/test/v1/students"))
                .andExpect(status().isOk());

        verify(appService, times(1)).getStudents(any());
    }

    @Test
    @WithMockUser(username = "ce8519fd-23f5-4eb3-80f9-85e5afec6b0d", roles = "USER")
    void getStudentsEmpty() throws Exception {

        when(appService.getStudents(any())).thenReturn(studentsEmpty);

        mockMvc.perform(get("/test/v1/students"))
                .andExpect(status().isOk());

        verify(appService, times(1)).getStudents(any());
    }

    @Test
    @WithAnonymousUser
    void getStudentsThrowsForbidden() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/test/v1/students"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "ce8519fd-23f5-4eb3-80f9-85e5afec6b0d", roles = "USER")
    void changeStudentSuccess() throws Exception {

        when(appService.changeStudent(any(), any(), any())).thenReturn(student);

        mockMvc.perform(put("/test/v1/student/" + studentId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(studentDto)))
                .andExpect(status().isOk());

        verify(appService, times(1)).changeStudent(any(), any(), any());
    }

    @Test
    @WithAnonymousUser
    void changeStudentThrowsForbidden() throws Exception {

        mockMvc.perform(put("/test/v1/student/" + studentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(studentDto)))
                        .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "ce8519fd-23f5-4eb3-80f9-85e5afec6b0d", roles = "USER")
    void changeStudentThrowEntityNotFound() throws Exception {

        when(appService.changeStudent(any(), any(), any())).thenThrow(new EntityNotFoundException(""));

        mockMvc.perform(put("/test/v1/student/" + studentId))
                .andExpect(status().isBadRequest());

        verify(appService, times(0)).changeStudent(any(), any(), any());
    }

    @Test
    @WithMockUser(username = "ce8519fd-23f5-4eb3-80f9-85e5afec6b0d", roles = "USER")
    void createStudentSuccess() throws Exception {

        when(appService.createStudent(any(), any())).thenReturn(student);

        mockMvc.perform(post("/test/v1/student/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(studentDto)))
                        .andExpect(status().isOk());

        verify(appService, times(1)).createStudent(any(), any());
    }

    @Test
    @WithAnonymousUser
    void createStudentThrowsForbidden() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/test/v1/student/new"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "ce8519fd-23f5-4eb3-80f9-85e5afec6b0d", roles = "USER")
    void deleteStudentSuccess() throws Exception {

        when(appService.deleteStudent(any(), any())).thenReturn(UUID.fromString(studentId));

        mockMvc.perform(delete("/test/v1/delete/" + studentId))
                .andExpect(status().isOk());

        verify(appService, times(1)).deleteStudent(any(), any());
    }

    @Test
    @WithAnonymousUser
    void deleteStudentThrowsForbidden() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.delete("/test/v1/delete/" + studentId))
                .andExpect(status().isForbidden());
    }
}
