package ru.test.service;

import ru.test.dto.TaskDto;
import ru.test.model.Task;

import java.util.List;
import java.util.UUID;

public interface AppService {

    List<Task> getTasks(String userOrAdmin);

    Task changeTask(UUID taskId, TaskDto taskDto, String userOrAdmin);

    Task createTask(TaskDto taskDto);

    Task deleteTask(UUID taskId);
}
