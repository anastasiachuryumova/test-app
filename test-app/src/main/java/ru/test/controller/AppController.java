package ru.test.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.test.dto.TaskDto;
import ru.test.service.AppService;

import java.security.Principal;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/test/v1/")
public class AppController {

    private final AppService appService;

    @GetMapping("tasks")
    public ResponseEntity<?> getTasks(
            Principal principal) {
        String userOrAdmin = principal.getName();
        return ResponseEntity.ok(appService.getTasks(userOrAdmin));
    }

    @PutMapping("task/{taskId}")
    public ResponseEntity<?> changeTask(
            @PathVariable("taskId") UUID taskId,
            @RequestBody @Valid TaskDto taskDto,
            Principal principal) {
        String userOrAdmin = principal.getName();
        return ResponseEntity.ok(appService.changeTask(taskId, taskDto, userOrAdmin));
    }

    @PostMapping("task/new")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> createTask (
            @RequestBody @Valid TaskDto taskDto) {
        return  ResponseEntity.ok(appService.createTask(taskDto));
    }

    @DeleteMapping ("delete/{taskId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> deleteTask(
            @PathVariable("taskId") UUID taskId) {
        return ResponseEntity.ok(appService.deleteTask(taskId));
    }
}