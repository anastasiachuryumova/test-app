package ru.test.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.test.config.SecurityConfig;
import ru.test.dto.TaskDto;
import ru.test.model.Task;
import ru.test.service.AppService;
import ru.test.util.TestData;

import java.util.List;

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

    private final List<Task> tasks = List.of(TestData.generateTask());
    private final List<Task> tasksEmpty = List.of();
    private final Task task = TestData.generateTask();
    private final TaskDto taskDto = TestData.generateTaskDto();
    private final String taskId = "123e4567-e89b-12d3-a456-426614174002";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private AppService appService;

    @Test
    @WithMockUser(username = "ce8519fd-23f5-4eb3-80f9-85e5afec6b0d", roles = "USER")
    void getStudentsSuccess() throws Exception {

        when(appService.getTasks(any())).thenReturn(tasks);

        mockMvc.perform(get("/test/v1/tasks"))
                .andExpect(status().isOk());

        verify(appService, times(1)).getTasks(any());
    }

    @Test
    @WithMockUser(username = "ce8519fd-23f5-4eb3-80f9-85e5afec6b0d", roles = "USER")
    void getStudentsEmpty() throws Exception {

        when(appService.getTasks(any())).thenReturn(tasksEmpty);

        mockMvc.perform(get("/test/v1/tasks"))
                .andExpect(status().isOk());

        verify(appService, times(1)).getTasks(any());
    }

    @Test
    @WithAnonymousUser
    void getStudentsThrowsForbidden() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/test/v1/tasks"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "ce8519fd-23f5-4eb3-80f9-85e5afec6b0d", roles = "USER")
    void changeStudentSuccess() throws Exception {

        when(appService.changeTask(any(), any(), any())).thenReturn(task);

        mockMvc.perform(put("/test/v1/task/" + taskId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(taskDto)))
                .andExpect(status().isOk());

        verify(appService, times(1)).changeTask(any(), any(), any());
    }

    @Test
    @WithAnonymousUser
    void changeStudentThrowsForbidden() throws Exception {

        mockMvc.perform(put("/test/v1/task/" + taskId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(taskDto)))
                        .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "ce8519fd-23f5-4eb3-80f9-85e5afec6b0d", roles = "USER")
    void changeStudentThrowEntityNotFound() throws Exception {

        when(appService.changeTask(any(), any(), any())).thenThrow(new EntityNotFoundException(""));

        mockMvc.perform(put("/test/v1/task/" + taskId))
                .andExpect(status().isBadRequest());

        verify(appService, times(0)).changeTask(any(), any(), any());
    }

    @Test
    @WithMockUser(username = "ce8519fd-23f5-4eb3-80f9-85e5afec6b0d", roles = "ADMIN")
    void createStudentSuccess() throws Exception {

        when(appService.createTask(any())).thenReturn(task);

        mockMvc.perform(post("/test/v1/task/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(taskDto)))
                        .andExpect(status().isOk());

        verify(appService, times(1)).createTask(any());
    }

    @Test
    @WithAnonymousUser
    void createStudentThrowsForbidden() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/test/v1/task/new"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "ce8519fd-23f5-4eb3-80f9-85e5afec6b0d", roles = "ADMIN")
    void deleteStudentSuccess() throws Exception {

        when(appService.deleteTask(any())).thenReturn(task);

        mockMvc.perform(delete("/test/v1/delete/" + taskId))
                .andExpect(status().isOk());

        verify(appService, times(1)).deleteTask(any());
    }

    @Test
    @WithAnonymousUser
    void deleteStudentThrowsForbidden() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.delete("/test/v1/delete/" + taskId))
                .andExpect(status().isForbidden());
    }
}
