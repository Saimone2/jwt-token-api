package com.saimone.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

class MainControllerTest {

    private MainController mainController;

    @BeforeEach
    public void setup() {
        mainController = new MainController();
    }

    @Test
    void sayHelloTest() {
        ResponseEntity<String> response = mainController.sayHello();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Now you have access to the main controller!", response.getBody());
    }
}