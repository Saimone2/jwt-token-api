package com.saimone.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api/v1/main-controller")
public class MainController {

    @GetMapping
    public ResponseEntity<String> sayHello() {
        log.info("IN main controller - the user accessed the controller using a token");
        return ResponseEntity.ok("Now you have access to the main controller!");
    }
}
