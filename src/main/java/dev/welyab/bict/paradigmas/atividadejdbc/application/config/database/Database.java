package dev.welyab.bict.paradigmas.atividadejdbc.application.config.database;

import com.google.common.base.Strings;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.pool.HikariPool;

import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class Database implements ConnectionFactory, Closeable {

    public static final String DATABASE_URL = "Database.URL";
    public static final String DATABASE_USER = "Database.USER";
    public static final String DATABASE_PASSWORD = "Database.PASSWORD";

    private static final String INVALID_PARAMETER_TEMPLATE = "Parameter not found: %s";

    private final HikariPool pool;

    public Database() {
        validateEnvironmentParameters();
        var config = new HikariConfig();
        config.setJdbcUrl(System.getProperty(DATABASE_URL));
        config.setUsername(System.getProperty(DATABASE_USER));
        config.setPassword(System.getProperty(DATABASE_PASSWORD));
        config.setMaximumPoolSize(50);
        pool = new HikariPool(config);
    }

    @Override
    public Connection createConnection() throws SQLException {
        return pool.getConnection();
    }

    @Override
    public void close() throws IOException {
        try {
            pool.shutdown();
        } catch (InterruptedException e) {
            throw new DatabaseException("Fail to close connection pool", e);
        }
    }

    private static void validateEnvironmentParameters() {
        if (Strings.isNullOrEmpty(System.getProperty(DATABASE_URL))) {
            throw new DatabaseException(String.format(INVALID_PARAMETER_TEMPLATE, DATABASE_URL));
        }
        if (Strings.isNullOrEmpty(System.getProperty(DATABASE_USER))) {
            throw new DatabaseException(String.format(INVALID_PARAMETER_TEMPLATE, DATABASE_USER));
        }
        if (Strings.isNullOrEmpty(System.getProperty(DATABASE_PASSWORD))) {
            throw new DatabaseException(String.format(INVALID_PARAMETER_TEMPLATE, DATABASE_PASSWORD));
        }
    }
}
