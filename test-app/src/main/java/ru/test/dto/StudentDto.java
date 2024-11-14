package ru.test.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class StudentDto {

    @NotNull
    private String name;

    @NotNull
    private String surname;

    @NotNull
    private String secondName;

    @NotNull
    private String group;

    @NotNull
    private double averageMark;
}
