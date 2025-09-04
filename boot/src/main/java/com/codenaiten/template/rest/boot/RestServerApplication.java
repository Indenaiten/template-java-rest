package com.codenaiten.template.rest.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan( basePackages = { "com.codenaiten.template.rest" })
public class RestServerApplication {

    // Tests
    //TODO: 07 - Añadir Tests unitarios al proyecto
    //TODO: 06 - Añadir Tests de arquitectura al proyecto con ArchJunit
    //TODO: 05 - Añadir Tests de integración al proyecto
    //TODO: 04 - Hacer un frontend simple para probar los endpoints (Registro, Login, Logout, Invalidar sesiones, Mostrar mi información y cambiar idioma)

    // Documentación
    //TODO: 03 - Revisar la documentación OpenAPI de la API Rest para mejorar si es posible
    //TODO: 02 - Añadir la máxima información al README.md del proyecto
    //TODO: 01 - Añadir un fichero CHANGELOG.md para listar los cambios del proyecto por versiones
    public static void main( final String[] args ) {
        SpringApplication.run( RestServerApplication.class, args );
    }
}
