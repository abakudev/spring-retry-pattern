package com.abakudev.retrypattern.retry;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RetryController {

    private final RetryService retryService;

    @GetMapping("/retry/{msg}")
    public ResponseEntity<String> callService(@PathVariable("msg") String msg)  {
        return ResponseEntity.ok(retryService.retryExample(msg));
    }
}
