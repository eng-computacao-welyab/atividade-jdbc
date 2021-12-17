package dev.welyab.bict.paradigmas.atividadejdbc;

import dev.welyab.bict.paradigmas.atividadejdbc.application.Application;
import dev.welyab.bict.paradigmas.atividadejdbc.application.config.database.Database;
import dev.welyab.bict.paradigmas.atividadejdbc.util.pagination.Page;
import dev.welyab.bict.paradigmas.atividadejdbc.core.entities.Movie;
import dev.welyab.bict.paradigmas.atividadejdbc.core.services.MovieService;

import java.math.BigDecimal;

import static dev.welyab.bict.paradigmas.atividadejdbc.DatabaseUtilities.createDatabaseStructure;
import static dev.welyab.bict.paradigmas.atividadejdbc.DatabaseUtilities.loadSampleData;
import static dev.welyab.bict.paradigmas.atividadejdbc.application.config.ioc.Ioc.instance;

@SuppressWarnings("java:S106")
public class MainDatabaseTest {

    public static void main(String[] args) throws Exception {
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
            movieService.findAll(new Page(0, Integer.MAX_VALUE)).getValues().forEach(System.out::println);
        }
    }
}
