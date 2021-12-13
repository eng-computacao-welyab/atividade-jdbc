package dev.welyab.bict.paradigmas.atividadejdbc.core.services;

import dev.welyab.bict.paradigmas.atividadejdbc.core.entities.Movie;

import java.util.List;

public interface MovieService {

    Movie findById(String id);

    List<Movie> findAll();

    List<Movie> findByIds(List<String> ids);

    void save(Movie movie);

    void update(Movie movie);
}
