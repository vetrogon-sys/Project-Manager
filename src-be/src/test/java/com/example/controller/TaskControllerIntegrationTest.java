package com.example.controller;

import com.example.config.AutoConfigureH2TestDatabase;
import com.example.config.FillDatabaseWithTestedData;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
@AutoConfigureWebTestClient
@AutoConfigureH2TestDatabase
@FillDatabaseWithTestedData
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TaskControllerIntegrationTest {

    @Autowired
    private WebTestClient webClient;

    @Test
    public void createTaskPositive() {
    }

    @Test
    public void moveTaskToAnotherDesk() {
    }

    @Test
    public void deleteTaskFromDesk() {
    }

    @Test
    public void getAllTasksFromDeskIfDeskExist() {

    }

    @Test
    public void updateTask() {

    }
}