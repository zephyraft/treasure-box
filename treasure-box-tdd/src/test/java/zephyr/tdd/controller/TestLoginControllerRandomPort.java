package zephyr.tdd.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import zephyr.tdd.TreasureBoxTddApp;

import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {TreasureBoxTddApp.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// 指定启动类
class TestLoginControllerRandomPort {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    void wrongPasswordShouldRedirectToErrorPage() {
        String responseString = testRestTemplate.getForObject("/login", String.class);
        assertEquals("{}", responseString);
    }

}
