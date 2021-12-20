package dev.welyab.bict.paradigmas.atividadejdbc.core.services;

import dev.welyab.bict.paradigmas.atividadejdbc.util.pagination.Page;
import dev.welyab.bict.paradigmas.atividadejdbc.util.pagination.PagedResult;
import dev.welyab.bict.paradigmas.atividadejdbc.core.entities.Movie;

import java.util.List;

public interface MovieService {

    Movie findById(String id);

    PagedResult<Movie> findAll(Page page);

    List<Movie> findByIds(List<String> ids);

    void save(Movie movie);

    void update(Movie movie);

    List<Movie> findAllLike(Movie movie);
}
