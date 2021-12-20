package dev.welyab.bict.paradigmas.atividadejdbc.application.repository.dao;

import dev.welyab.bict.paradigmas.atividadejdbc.core.entities.Movie;

import java.util.List;

public interface MoviesDao extends Dao<Movie, String> {
    List<Movie> findAllLike(Movie movie);
}
