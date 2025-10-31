package com.example.LunaSpa.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.context.WebServerApplicationContext;

@Component
public class ServerInfoPrinter implements CommandLineRunner {

    @Autowired
    private WebServerApplicationContext webServerAppContext;

    @Override
    public void run(String... args) {
        int port = webServerAppContext.getWebServer().getPort();
        System.out.println("====================================================");
        System.out.println("La aplicacion se esta ejecutando correctamente");
        System.out.println("URL de acceso: http://localhost:" + port);
        System.out.println("====================================================");
    }
}
