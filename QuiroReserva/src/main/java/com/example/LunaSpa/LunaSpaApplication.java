package com.example.LunaSpa;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class LunaSpaApplication implements CommandLineRunner {

    @Autowired
    private Environment env;

    @Autowired
    private ApplicationContext applicationContext;

    public static void main(String[] args) {
        SpringApplication.run(LunaSpaApplication.class, args);
    }

    @Override
    public void run(String... args) {
        String port = env.getProperty("local.server.port");
        if (port == null) {
            port = env.getProperty("server.port", "8080");
        }
        System.out.println("\n----------------------------------------------------------");
        System.out.println(" La aplicación se está ejecutando correctamente ");
        System.out.println(" URL de acceso: http://localhost:" + port);
        System.out.println("----------------------------------------------------------\n");
    }
}
