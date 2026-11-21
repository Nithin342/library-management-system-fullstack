package com.library.managament.config;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class DatabaseConfigurationTest {

    @Test
    void convertsRenderPostgresUrlToJdbcAndUsesItsCredentials() {
        DatabaseConfiguration.DatabaseSettings settings = DatabaseConfiguration.normalize(
                "postgresql://library_user:pa%3Ass@db.example:5432/library_db?sslmode=require",
                "fallback-user",
                "fallback-password");

        assertEquals(
                "jdbc:postgresql://db.example:5432/library_db?sslmode=require",
                settings.url());
        assertEquals("library_user", settings.username());
        assertEquals("pa:ss", settings.password());
    }

    @Test
    void leavesJdbcUrlsAndTheirSeparateCredentialsUnchanged() {
        DatabaseConfiguration.DatabaseSettings settings = DatabaseConfiguration.normalize(
                "jdbc:mysql://localhost:3306/library_db",
                "library_user",
                "library-password");

        assertEquals("jdbc:mysql://localhost:3306/library_db", settings.url());
        assertEquals("library_user", settings.username());
        assertEquals("library-password", settings.password());
    }
}
