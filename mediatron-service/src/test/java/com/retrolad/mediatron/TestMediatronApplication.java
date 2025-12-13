package com.retrolad.mediatron;

import org.springframework.boot.SpringApplication;

public class TestMediatronApplication {

    public static void main(String[] args) {
        SpringApplication.from(MediatronApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
