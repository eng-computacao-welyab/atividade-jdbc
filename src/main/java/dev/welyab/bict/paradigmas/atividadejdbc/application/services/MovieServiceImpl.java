package dev.welyab.bict.paradigmas.atividadejdbc.application.services;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import dev.welyab.bict.paradigmas.atividadejdbc.application.exception.ApplicationException;
import dev.welyab.bict.paradigmas.atividadejdbc.application.repository.dao.MoviesDao;
import dev.welyab.bict.paradigmas.atividadejdbc.util.pagination.Page;
import dev.welyab.bict.paradigmas.atividadejdbc.util.pagination.PagedResult;
import dev.welyab.bict.paradigmas.atividadejdbc.core.entities.Movie;
import dev.welyab.bict.paradigmas.atividadejdbc.core.services.MovieService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class MovieServiceImpl implements MovieService {

    private static final Logger logger = LogManager.getLogger(MovieServiceImpl.class);

    private final MoviesDao moviesDao;

    public MovieServiceImpl(MoviesDao moviesDao) {
        this.moviesDao = moviesDao;
    }

    @Override
    public Movie findById(String id) {
        Preconditions.checkNotNull(id, "id");
        return moviesDao.find(id);
    }

    @Override
    public PagedResult<Movie> findAll(Page page) {
        return moviesDao.findAll(page);
    }

    @Override
    public List<Movie> findByIds(List<String> ids) {
        Preconditions.checkNotNull(ids, "ids");
        if (ids.isEmpty()) return List.of();
        return moviesDao.find(ids);
    }

    @Override
    public void save(Movie movie) {
        logger.info("Movie receive to save");
        Preconditions.checkNotNull(movie, "movie");

        if (!Strings.isNullOrEmpty(movie.getId())) {
            throw new ApplicationException(String.format("Fail to save movie: %s. Id should not be informed", movie));
        }

        movie.setId(UUID.randomUUID().toString());
        movie.setRegistrationDate(LocalDate.now());
        moviesDao.save(movie);
        logger.info("Movie saved successful");
    }

    @Override
    public void update(Movie movie) {
        Preconditions.checkNotNull(movie, "movie");
        moviesDao.update(movie);
    }

    @Override
    public List<Movie> findAllLike(Movie movie) {
        return moviesDao.findAllLike(movie);
    }
}
