package com.abakudev.retrypattern;

import com.abakudev.retrypattern.retry.ExampleController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class RetryPatternAppTests {

	@Autowired
	ExampleController exampleController;

	@Test
	void contextLoads() {
		Assertions.assertNotNull(exampleController);
	}

}
