package com.abakudev.retrypattern.retry;

import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Slf4j
@Service
public class RetryService {

    private int intentos = 0;

    @Retryable(value = {RuntimeException.class},
            maxAttempts = 3,
            backoff = @Backoff(multiplier = 2))
    public String retryExample(String s) {
        log.info("Intento #{} {} ", (intentos++), Instant.now());
        if (s.equals("error")) {
            throw new RuntimeException("Error llamando a retryService.");
        }
        log.info("Acción realizada");
        return "OK";
    }
    @Recover
    public String retryExampleRecovery(RuntimeException t, String s) {
        log.info("Retry Recovery - {}", t.getMessage());
        log.info("Recuperada la llamada al servicio que falla");
        return "Hello from fallback method!!!";
    }

}
