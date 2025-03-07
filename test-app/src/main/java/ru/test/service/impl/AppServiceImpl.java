package ru.test.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import ru.test.dto.TaskDto;
import ru.test.model.UserStatus;
import ru.test.model.Task;
import ru.test.model.User;
import ru.test.repo.TaskRepository;
import ru.test.repo.UserRepository;
import ru.test.service.AppService;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AppServiceImpl implements AppService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    @Override
    public List<Task> getTasks(String userOrAdmin) {
        if (userOrAdmin.equals("ADMIN")){
            return taskRepository.findAll();
        } else {
            User user = userRepository.findByUserStatus(UserStatus.USER)
                    .orElseThrow(EntityNotFoundException::new);
            return Collections.singletonList(taskRepository.findByExecutor(user.getName())
                    .orElseThrow(EntityNotFoundException::new));
        }
    }

    @Override
    public Task changeTask(UUID taskId, TaskDto taskDto, String userOrAdmin) {
        if (userOrAdmin.equals("ADMIN")){
            Task task = taskRepository.findById(taskId).orElseThrow(EntityNotFoundException::new);
            return getTask(taskId, taskDto, task);
        } else {
            User user = userRepository.findByUserStatus(UserStatus.USER)
                    .orElseThrow(EntityNotFoundException::new);
            Task task = taskRepository.findByIdAndExecutor(taskId, user.getName()).orElseThrow(EntityNotFoundException::new);
            return getTask(taskId, taskDto, task);
        }
    }

    @NotNull
    private Task getTask(UUID taskId, TaskDto taskDto, Task task) {
        Task newTask = Task.builder()
                .id(task.getId())
                .title(taskDto.getTitle())
                .description(taskDto.getDescription())
                .taskStatus(taskDto.getTaskStatus())
                .priority(taskDto.getPriority())
                .commentary(taskDto.getCommentary())
                .author(taskDto.getAuthor())
                .executor(taskDto.getExecutor())
                .build();
        taskRepository.deleteById(taskId);
        taskRepository.save(newTask);
        return newTask;
    }

    @Override
    public Task createTask(TaskDto taskDto) {
        Task newTask = Task.builder()
                .title(taskDto.getTitle())
                .description(taskDto.getDescription())
                .taskStatus(taskDto.getTaskStatus())
                .priority(taskDto.getPriority())
                .commentary(taskDto.getCommentary())
                .author(taskDto.getAuthor())
                .executor(taskDto.getExecutor())
                .build();
        taskRepository.save(newTask);
        return newTask;
    }

    @Override
    public Task deleteTask(UUID taskId) {
        Task taskDeleted = taskRepository.findById(taskId).orElseThrow(EntityNotFoundException::new);
        taskRepository.deleteById(taskId);
        return taskDeleted;

    }
}
