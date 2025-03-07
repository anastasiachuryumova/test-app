package ru.test.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "tasks", schema = "test_schema")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "stat", nullable = false, columnDefinition = "varchar(32)")
    @Enumerated(EnumType.STRING)
    private TaskStatus taskStatus;

    @Column(name = "priority", nullable = false, columnDefinition = "varchar(32)")
    @Enumerated(EnumType.STRING)
    private Priority priority;

    @Column(name = "commentary", nullable = false)
    private String commentary;

    @Column(name = "author", nullable = false)
    private String author;

    @Column(name = "executor", nullable = false)
    private String executor;
}
