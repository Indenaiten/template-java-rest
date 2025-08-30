package com.codenaiten.template.rest.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan( basePackages = { "com.codenaiten.template.rest" })
public class RestServerApplication {

    // Logica
    //TODO: 10 - Añadir campo a la tabla de usuarios para almacenar una imagen de perfil del usuario

    // Tests
    //TODO: 09 - Añadir Tests unitarios al proyecto
    //TODO: 08 - Añadir Tests de arquitectura al proyecto con ArchJunit
    //TODO: 07 - Añadir Tests de integración al proyecto
    //TODO: 06 - Hacer un frontend simple para probar los endpoints (Registro, Login, Logout, Invalidar sesiones, Mostrar mi información y cambiar idioma)

    // Documentación
    //TODO: 05 - Añadir JavaDoc al código para establecer una buena documentación inicial
    //TODO: 04 - Revisar la documentación OpenAPI de la API Rest para mejorar si es posible
    //TODO: 03 - Añadir la información de los desarrolladores a los ficheros pom.xml del proyecto
    //TODO: 02 - Añadir la máxima información al README.md del proyecto
    //TODO: 01 - Añadir un fichero CHANGELOG.md para listar los cambios del proyecto por versiones
    public static void main( final String[] args ) {
        SpringApplication.run( RestServerApplication.class, args );
    }
}
