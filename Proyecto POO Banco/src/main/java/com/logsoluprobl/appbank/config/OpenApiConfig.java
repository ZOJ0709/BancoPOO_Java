package com.logsoluprobl.appbank.config;

import javax.sound.midi.MidiDevice.Info;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    public OpenAPI bankAppOpenAPI() {
        return new OpenAPI()
            .info(new io.swagger.v3.oas.models.info.Info()
            .title("Mi app de banco")
            .description("Esta es una aplicacion de banco creada para el curso de POO")
            .version("1.0.0")
            .contact(new Contact()
                .name("Jeronimo Ospina")
                .email("Jerospiza@gmail.com")
                .url("https://github.com/ZOJ0709/BancoPOO_Java.git"))
                .license(new License()
                    .name("Apache 2.0")
                    .url("http://springdoc.org")));

    }

    
}

