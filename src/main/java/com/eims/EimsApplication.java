package com.eims;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication(scanBasePackages = {"com.eims","com.eims.repository"})
@EntityScan("com.eims.entity")
@ServletComponentScan(basePackages = "com.eims.filter")
public class EimsApplication {

    public static void main(String[] args) {
        SpringApplication.run(EimsApplication.class, args);
    }

}
