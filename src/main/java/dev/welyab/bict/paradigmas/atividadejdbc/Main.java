package dev.welyab.bict.paradigmas.atividadejdbc;

import dev.welyab.bict.paradigmas.atividadejdbc.application.Application;
import dev.welyab.bict.paradigmas.atividadejdbc.application.config.database.ConnectionFactory;
import dev.welyab.bict.paradigmas.atividadejdbc.application.config.database.Database;
import dev.welyab.bict.paradigmas.atividadejdbc.application.config.database.DatabaseException;
import dev.welyab.bict.paradigmas.atividadejdbc.application.config.ioc.Ioc;
import dev.welyab.bict.paradigmas.atividadejdbc.core.entities.Movie;
import dev.welyab.bict.paradigmas.atividadejdbc.core.services.MovieService;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.UUID;

import static dev.welyab.bict.paradigmas.atividadejdbc.application.config.ioc.Ioc.instance;

public class Main {

    public static void main(String[] args) throws Exception {
        System.out.println(UUID.randomUUID().toString());

        // database parameters
        System.setProperty(Database.DATABASE_URL, "jdbc:hsqldb:mem:movies");
        System.setProperty(Database.DATABASE_USER, "sa");
        System.setProperty(Database.DATABASE_PASSWORD, "sa");

        try (var ignored = new Application()) {
            createDatabaseStructure();
            loadSampleData();

            var movieService = instance(MovieService.class, Application.Components.MOVIES_SERVICE);

            movieService.save(
                    new Movie(
                            null,
                            "Vingadores: Ultimato",
                            3019,
                            new BigDecimal("8.4"),
                            "https://www.imdb.com/title/tt4154796/"
                    )
            );
            movieService.findAll().forEach(movie -> {
                System.out.println(movie);
            });
        }
    }

    private static void createDatabaseStructure() throws IOException, SQLException {
        executeSqlScript(loadSqlScript("create_database_structure.sql"));
    }

    private static void loadSampleData() throws IOException, SQLException {
        executeSqlScript(loadSqlScript("sample_data.sql"));
    }

    private static void executeSqlScript(String sql) throws SQLException {
        var connFactory = Ioc.<ConnectionFactory>instance(Application.Components.CONNECTION_FACTORY);
        try (var conn = connFactory.createConnection()) {
            try {
                conn.setAutoCommit(false);
                for (var sqlPart : sql.split(";")) {
                    if (sqlPart.trim().isBlank()) continue;
                    try (var stm = conn.createStatement()) {
                        try {
                            stm.execute(sqlPart);
                        } catch (SQLException e) {
                            throw new DatabaseException(String.format("Fail to execute script: %s", sqlPart), e);
                        }
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
        try (var script = Main.class.getClassLoader().getResourceAsStream(String.format("database/%s", fileName))) {
            return IOUtils.toString(script, StandardCharsets.UTF_8);
        }
    }
}
