package com.studymentor.studymentor.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;

import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(

        info = @Info(
                title = "StudyMentor API",

                version = "1.0.0",

                description = """
                        API REST educacional desenvolvida
                        com Java, Spring Boot e Spring AI.

                        Funcionalidades:
                        - Verificacao do status da aplicacao
                        - Geracao de exercicios com IA
                        - Geracao de questionarios estruturados
                        - Correcao automatica de respostas

                        Os questionarios sao armazenados
                        temporariamente em memoria.
                        """,

                contact = @Contact(
                        name = "Equipe StudyMentor"
                ),

                license = @License(
                        name = "Licenca a definir"
                )
        ),

        servers = {
                @Server(
                        url = "http://localhost:8080",
                        description = "Servidor local de desenvolvimento"
                )
        }
)
public class OpenApiConfig {
}
