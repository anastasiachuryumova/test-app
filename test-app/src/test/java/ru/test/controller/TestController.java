package ru.test.controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping
    public TestDto test() {
        return new TestDto("test");
    }

}

class TestDto {
    private final String message;

    public TestDto(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
