package com.eims.controller.test;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1")
@Slf4j
public class Test {

    @GetMapping(value = "/test")
    public String Test() {
        return "Eims Project Test!!";
    };
}
