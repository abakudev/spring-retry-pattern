package com.abakudev.retrypattern.retry;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@EnableRetry
class ExampleServiceTest {

    @Autowired
    private ExampleService exampleService;

    @Test
    void testRetry() {
        String message = this.exampleService.retryExample("Abakudev");
        assertEquals("Hi Abakudev", message);
    }

    @Test
    void retryExampleWithRecoveryTest() throws Exception {
        String result = exampleService.retryExample("error");
        assertEquals("Retry Recovery OK!", result);
    }

}
