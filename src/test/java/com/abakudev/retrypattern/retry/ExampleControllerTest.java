package com.abakudev.retrypattern.retry;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ExampleController.class)
@DisplayName("Example Controller Test")
class ExampleControllerTest {

    @MockBean
    ExampleService exampleService;

    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("retryExample")
    void whenCallService_ThenReturnOk() throws Exception {
        when(this.exampleService.retryExample(anyString())).thenReturn("Hi Abakudev");
        mockMvc.perform(get("/retry/{name}", "Abakudev"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.is("Hi Abakudev")));
    }
}
