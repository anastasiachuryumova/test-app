package ru.test.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.test.model.Priority;
import ru.test.model.TaskStatus;

@Setter
@Getter
@AllArgsConstructor
public class TaskDto {
    @NotNull
    private String title;

    @NotNull
    private String description;

    @NotNull
    private TaskStatus taskStatus;

    @NotNull
    private Priority priority;

    @NotNull
    private String commentary;

    @NotNull
    private String author;

    @NotNull
    private String executor;
}
