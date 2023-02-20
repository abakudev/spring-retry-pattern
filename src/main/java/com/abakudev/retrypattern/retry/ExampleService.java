package com.abakudev.retrypattern.retry;

import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Objects;

@Slf4j
@Service
public class ExampleService {

    private int intentos = 0;

    @Retryable(value = {RuntimeException.class},
            maxAttemptsExpression = "${retry.maxAttempts}",
            backoff = @Backoff(value = 1000, delayExpression = "${retry.maxDelay}"))
    public String retryExample(String name) {
        log.info("Attempt #{} {} ", (++intentos), Instant.now());
        if (Objects.equals(name, "error")) {
            throw new RetryException("Error in RetryExampleService.retryExample ");
        } else {
            return "Hi " + name;
        }
    }
    @Recover
    public String retryExampleRecovery(RuntimeException t, String s) {
        log.info("Retry Recovery - {}", t.getMessage());
        return "Retry Recovery OK!";
    }
}
