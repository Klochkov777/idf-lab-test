package com.klochkov.idflabtest.integration.service;

import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.PostgreSQLContainer;


@ActiveProfiles("test")
public abstract class AbstractTestIT {

@ServiceConnection
private static final PostgreSQLContainer<?> POSTGRESQL_CONTAINER = new PostgreSQLContainer<>(
        "postgres:15-alpine");

    static {
        POSTGRESQL_CONTAINER.start();
    }
}
