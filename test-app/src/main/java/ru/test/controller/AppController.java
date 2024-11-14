package ru.test.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.test.dto.StudentDto;
import ru.test.service.AppService;

import java.security.Principal;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/test/v1/")
public class AppController {

    private final AppService appService;

    @GetMapping("students")
    public ResponseEntity<?> getStudents(
            Principal principal) {
        UUID userId = UUID.fromString(principal.getName());
        return ResponseEntity.ok(appService.getStudents(userId));
    }

    @PutMapping("student/{studentId}")
    public ResponseEntity<?> changeStudent(
            @PathVariable("studentId") UUID studentId,
            @RequestBody @Valid StudentDto studentDto,
            Principal principal) {
        UUID userId = UUID.fromString(principal.getName());
        return ResponseEntity.ok(appService.changeStudent(userId, studentId, studentDto));
    }

    @PostMapping("student/new")
    public ResponseEntity<?> createStudent(
            @RequestBody @Valid StudentDto studentDto,
            Principal principal) {
        UUID userId = UUID.fromString(principal.getName());
        return ResponseEntity.ok(appService.createStudent(userId, studentDto));
    }

    @DeleteMapping ("delete/{studentId}")
    public ResponseEntity<?> deleteStudent(
            @PathVariable("studentId") UUID studentId,
            Principal principal) {
        UUID userId = UUID.fromString(principal.getName());
        return ResponseEntity.ok(appService.deleteStudent(userId, studentId));
    }
}