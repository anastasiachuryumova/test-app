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
import ru.test.dto.TaskDto;
import ru.test.model.Task;
import ru.test.model.User;
import ru.test.repo.TaskRepository;
import ru.test.repo.UserRepository;
import ru.test.service.impl.AppServiceImpl;
import ru.test.util.TestData;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AppServiceTest {
    @Mock
    private UserRepository userRepository;
    @Captor
    private ArgumentCaptor<User> userArgumentCaptor;
    @Mock
    private TaskRepository taskRepository;
    @Captor
    private ArgumentCaptor<Task> taskArgumentCaptor;

    @InjectMocks
    private AppServiceImpl appService;

    private final UUID taskId = UUID.fromString("123e4567-e89b-12d3-a456-426614174002");
    private final TaskDto taskDto = TestData.generateTaskDto();
    private final User user = TestData.generateUser();
    private final User admin = TestData.generateAdmin();
    private final Task task = TestData.generateTask();
    private final List<Task> tasks = List.of(task);

    @Test
    void createTaskSuccess() {

        when(taskRepository.save(any())).thenReturn(task);
        var actualResult = appService.createTask(taskDto);
        assertThat(actualResult.getExecutor()).isEqualTo(task.getExecutor());

        verify(taskRepository, times(1)).save(taskArgumentCaptor.capture());
        var taskArgumentCaptorValue = taskArgumentCaptor.getValue();
        assertThat(taskArgumentCaptorValue).isNotNull();
        assertThat(taskArgumentCaptorValue.getAuthor()).isEqualTo(task.getAuthor());
    }

    @Test
    void createTaskThrowEntityExists() {

        when(taskRepository.save(any())).thenThrow(new EntityExistsException(""));
        assertThatExceptionOfType(EntityExistsException.class)
                .isThrownBy(() -> {
                    appService.createTask(taskDto);
                }).withMessage("");
    }

    @Test
    void getTasksSuccess() {

        when(taskRepository.findAll()).thenReturn(tasks);
        //when(userRepository.findByUserStatus(any())).thenReturn(Optional.of(user));

        var actualResult = appService.getTasks("ADMIN");
        assertThat(actualResult.size()).isEqualTo(1);
        verify(taskRepository, times(1)).findAll();
    }

    @Test
    void changeTaskSuccess() {

        when(taskRepository.findById(any())).thenReturn(Optional.of(task));
        when(taskRepository.save(any())).thenReturn(task);

        var actualResult = appService.changeTask(taskId, taskDto, admin.getUserStatus().toString());
        assertThat(actualResult.getPriority()).isEqualTo(task.getPriority());
        assertThat(actualResult.getAuthor()).isEqualTo(task.getAuthor());
        assertThat(actualResult.getDescription()).isEqualTo(task.getDescription());
        assertThat(actualResult.getCommentary()).isEqualTo(task.getCommentary());
        assertThat(actualResult.getExecutor()).isEqualTo(task.getExecutor());

        verify(taskRepository, times(1)).findById(taskId);
        verify(taskRepository, times(1)).save(taskArgumentCaptor.capture());

        var taskArgumentCaptorValue = taskArgumentCaptor.getValue();

        assertThat(taskArgumentCaptorValue).isNotNull();
        assertThat(taskArgumentCaptorValue.getTaskStatus()).isEqualTo(task.getTaskStatus());
        assertThat(taskArgumentCaptorValue.getExecutor()).isEqualTo(task.getExecutor());
        assertThat(taskArgumentCaptorValue.getTitle()).isEqualTo(task.getTitle());
        assertThat(taskArgumentCaptorValue.getAuthor()).isEqualTo(task.getAuthor());
        assertThat(taskArgumentCaptorValue.getDescription()).isEqualTo(task.getDescription());
    }

    @Test
    void changeTaskThrowEntityNotFound() {

        when(taskRepository.findById(any())).thenThrow(new EntityNotFoundException(""));

        assertThatExceptionOfType(EntityNotFoundException.class)
                .isThrownBy(() -> {
                    appService.changeTask(taskId, taskDto, admin.getUserStatus().toString());
                }).withMessage("");

        verify(taskRepository, times(1)).findById(taskId);
        verify(taskRepository, times(0)).save(task);
    }

    @Test
    void deleteTaskSuccess() {

        when(taskRepository.findById(any())).thenReturn(Optional.of(task));

        var actualResult = appService.deleteTask(taskId);
        assertThat(actualResult).isEqualTo(task);
        verify(taskRepository, times(1)).deleteById(any());
    }

    @Test
    void getTaskThrowEntityNotFound() {

        when(taskRepository.findById(any())).thenThrow(new EntityNotFoundException(""));

        assertThatExceptionOfType(EntityNotFoundException.class)
                .isThrownBy(() -> {
                    appService.deleteTask(taskId);
                }).withMessage("");
        verify(taskRepository, times(0)).deleteById(any());
    }
}
