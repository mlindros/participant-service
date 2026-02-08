package com.example.participantservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.jdbc.autoconfigure.JndiDataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@EnableAutoConfiguration(exclude = { JndiDataSourceAutoConfiguration.class })
class ParticipantServiceApplicationTests {

    @Test
    void contextLoads() {
    }

}
