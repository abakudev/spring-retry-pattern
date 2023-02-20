package com.abakudev.retrypattern.retry;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ExampleController {

    private final ExampleService exampleService;

    @GetMapping("/retry/{name}")
    public ResponseEntity<String> callService(@PathVariable("name") String name)  {
        return ResponseEntity.ok(exampleService.retryExample(name));
    }
}
