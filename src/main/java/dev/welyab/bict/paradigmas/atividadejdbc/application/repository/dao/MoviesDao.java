package dev.welyab.bict.paradigmas.atividadejdbc.application.repository.dao;

import dev.welyab.bict.paradigmas.atividadejdbc.core.entities.Movie;

import java.util.List;

public interface MoviesDao {

    Movie findById(String id);

    List<Movie> findAll();

    List<Movie> findByIds(List<String> ids);

    void save(Movie movie);

    void update(Movie movie);
}
