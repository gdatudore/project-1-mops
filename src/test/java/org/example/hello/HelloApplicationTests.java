package org.example.hello;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class HelloApplicationTests {

    @MockBean
    InformationRepository mockRepository;

    @Test
    void contextLoads() {
    }

}