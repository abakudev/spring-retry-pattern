package com.abakudev.retrypattern.retry_template;

import com.abakudev.retrypattern.retry.RetryException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@DisplayName("Retry Template Example Service Test")
class RetryTemplateExampleServiceTest {

    @Autowired
    private RetryTemplateExampleService retryTemplateExampleService;

    @Test
    @DisplayName("Retry Template Example Should Throw RetryException")
    void retryTemplateExampleShouldThrowRuntime() throws Exception {

        RetryException exception = assertThrows(RetryException.class, () -> {
            retryTemplateExampleService.retryTemplateExample("error");
        });

        String expectedMessage = "Error in process";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    @DisplayName("Retry Template Example Should Return Correct Value")
    void retryTemplateExampleShouldReturnCorrectValue() throws Exception {
        String s = retryTemplateExampleService.retryTemplateExample("word");
        assertEquals(s, "Hi word");
    }
}
