package com.codenaiten.template.rest.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan( basePackages = { "com.codenaiten.template.rest" })
public class RestServerApplication {

    public static void main( final String[] args ) {
        SpringApplication.run( RestServerApplication.class, args );
    }
}
