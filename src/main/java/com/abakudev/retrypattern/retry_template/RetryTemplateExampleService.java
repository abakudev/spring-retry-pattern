package com.abakudev.retrypattern.retry_template;

import com.abakudev.retrypattern.retry.RetryException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class RetryTemplateExampleService {

    private final RetryTemplate retryTemplate;

    public String retryTemplateExample(String name) {
        String result;
        result = retryTemplate.execute(retryContext -> {
            // do something in this service
            log.info(String.format("Retry retryTemplateExample %d", LocalDateTime.now().getSecond()));
            if (Objects.equals(name, "error")) {
                throw new RetryException("Error in process");
            } else {
                return "Hi " + name;
            }
        });
        log.info("Returning {}", result);
        return result;
    }
}
