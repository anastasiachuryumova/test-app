package ru.test.util;

import ru.test.dto.TaskDto;
import ru.test.model.*;

import java.util.UUID;

public class TestData {

    public static TaskDto generateTaskDto() {
        return new TaskDto(
                "new task",
                "task description",
                TaskStatus.IN_PROGRESS,
                Priority.HIGH,
                "commentary",
                "author",
                "executor");
    }

    public static Task generateTask(){
        return new Task(
                UUID.fromString("123e4567-e89b-12d3-a456-426614174002"),
                "new task",
                "task description",
                TaskStatus.IN_PROGRESS,
                Priority.HIGH,
                "commentary",
                "author",
                "executor");
    }

    public static User generateUser(){
        return new User(
                UUID.fromString("123e4567-e89b-12d3-a456-426614174002"),
                "executor",
                "executor@mail.com",
                "32141351351235135",
                UserStatus.USER);
    }

    public static User generateAdmin(){
        return new User(
                UUID.fromString("123e4567-e89b-12d3-a456-426614174002"),
                "executor",
                "executor@mail.com",
                "32141351351235135",
                UserStatus.ADMIN);
    }
}
