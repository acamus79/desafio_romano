package com.aec.romanos.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Adrian E. Camus",
                        email = "acamus@tutamail.com",
                        url = "https://acamus79.github.io/"
                ),
                description = "OpenApi documentation for SimpleFleet API",
                title = "Kata Números romanos  - API REST",
                version = "0.1.01",
                license = @License(
                        name = "License Apache 2.0",
                        url = "https://www.apache.org/licenses/LICENSE-2.0.html"
                )
        ),
        servers = {
                @Server(
                        description = "Local ENV",
                        url = "http://localhost:8080"
                ),
                @Server(
                        description = "PROD ENV",
                        url = "COMING SOON"
                )
        }

)
public class OpenApiConfig {
}
