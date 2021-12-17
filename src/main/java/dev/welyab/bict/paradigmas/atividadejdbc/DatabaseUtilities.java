package dev.welyab.bict.paradigmas.atividadejdbc;

import dev.welyab.bict.paradigmas.atividadejdbc.application.Application;
import dev.welyab.bict.paradigmas.atividadejdbc.application.config.database.ConnectionFactory;
import dev.welyab.bict.paradigmas.atividadejdbc.application.config.database.DatabaseException;
import dev.welyab.bict.paradigmas.atividadejdbc.application.config.ioc.Ioc;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

public class DatabaseUtilities {

    public static void createDatabaseStructure() throws IOException, SQLException {
        executeSqlScript(loadSqlScript("create_database_structure.sql"));
    }

    public static void loadSampleData() throws IOException, SQLException {
        executeSqlScript(loadSqlScript("sample_data.sql"));
    }

    public static void executeSqlScript(String sql) throws SQLException {
        var connFactory = Ioc.<ConnectionFactory>instance(Application.Components.CONNECTION_FACTORY);
        try (var conn = connFactory.createConnection()) {
            try {
                conn.setAutoCommit(false);
                for (var sqlPart : sql.split(";")) {
                    if (sqlPart.trim().isBlank()) continue;
                    try (var stm = conn.createStatement()) {
                        stm.execute(sqlPart);
                    }
                }
                conn.commit();
            } catch (Exception e) {
                conn.rollback();
                throw new DatabaseException("Fail to execute script", e);
            }
        }
    }

    private static String loadSqlScript(String fileName) throws IOException {
        try (var script = MainDatabaseTest.class.getClassLoader().getResourceAsStream(String.format("database/%s", fileName))) {
            return IOUtils.toString(script, StandardCharsets.UTF_8);
        }
    }
}
