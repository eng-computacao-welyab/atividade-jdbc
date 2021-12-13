package dev.welyab.bict.paradigmas.atividadejdbc.application.services;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import dev.welyab.bict.paradigmas.atividadejdbc.application.exception.ApplicationException;
import dev.welyab.bict.paradigmas.atividadejdbc.application.repository.dao.MoviesDao;
import dev.welyab.bict.paradigmas.atividadejdbc.core.entities.Movie;
import dev.welyab.bict.paradigmas.atividadejdbc.core.services.MovieService;

import java.util.List;
import java.util.UUID;

public class MovieServiceImpl implements MovieService {

    private final MoviesDao moviesDao;

    public MovieServiceImpl(MoviesDao moviesDao) {
        this.moviesDao = moviesDao;
    }

    @Override
    public Movie findById(String id) {
        Preconditions.checkNotNull(id, "code");
        return moviesDao.findById(id);
    }

    @Override
    public List<Movie> findAll() {
        return moviesDao.findAll();
    }

    @Override
    public List<Movie> findByIds(List<String> ids) {
        Preconditions.checkNotNull(ids, "ids");
        if (ids.isEmpty()) return List.of();
        return moviesDao.findByIds(ids);
    }

    @Override
    public void save(Movie movie) {
        Preconditions.checkNotNull(movie, "movie");

        if (!Strings.isNullOrEmpty(movie.getId())) {
            throw new ApplicationException(String.format("Fail to save movie: %s. Id should not be informed", movie));
        }

        movie.setId(UUID.randomUUID().toString());
        moviesDao.save(movie);
    }

    @Override
    public void update(Movie movie) {
        Preconditions.checkNotNull(movie, "movie");
        moviesDao.update(movie);
    }
}
