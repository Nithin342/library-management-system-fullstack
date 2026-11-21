package com.library.managament.config;

import java.net.URI;
import java.util.Locale;

import javax.sql.DataSource;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class DatabaseConfiguration {

    @Bean
    public DataSource dataSource(Environment environment) {
        String url = environment.getRequiredProperty("spring.datasource.url");
        String username = environment.getProperty("spring.datasource.username");
        String password = environment.getProperty("spring.datasource.password");
        DatabaseSettings settings = normalize(url, username, password);

        return DataSourceBuilder.create()
                .url(settings.url())
                .username(settings.username())
                .password(settings.password())
                .build();
    }

    static DatabaseSettings normalize(String url, String username, String password) {
        URI uri = URI.create(url);
        String scheme = uri.getScheme();
        if (scheme == null) {
            return new DatabaseSettings(url, username, password);
        }

        String normalizedScheme = scheme.toLowerCase(Locale.ROOT);
        if (!normalizedScheme.equals("postgres") && !normalizedScheme.equals("postgresql")) {
            return new DatabaseSettings(url, username, password);
        }

        String authority = uri.getRawAuthority();
        String userInfo = uri.getUserInfo();
        if (authority == null || uri.getHost() == null || uri.getRawPath() == null
                || uri.getRawPath().length() < 2) {
            throw new IllegalArgumentException("DATABASE_URL must include a PostgreSQL host and database name");
        }

        if (userInfo != null) {
            int separator = userInfo.indexOf(':');
            username = separator < 0 ? userInfo : userInfo.substring(0, separator);
            password = separator < 0 ? "" : userInfo.substring(separator + 1);
            authority = authority.substring(authority.lastIndexOf('@') + 1);
        }

        String jdbcUrl = "jdbc:postgresql://" + authority + uri.getRawPath();
        if (uri.getRawQuery() != null) {
            jdbcUrl += "?" + uri.getRawQuery();
        }
        return new DatabaseSettings(jdbcUrl, username, password);
    }

    record DatabaseSettings(String url, String username, String password) {
    }
}
