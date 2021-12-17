package dev.welyab.bict.paradigmas.atividadejdbc.application.repository.dao.impl;

import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import dev.welyab.bict.paradigmas.atividadejdbc.application.config.database.ConnectionFactory;
import dev.welyab.bict.paradigmas.atividadejdbc.application.repository.dao.DaoException;
import dev.welyab.bict.paradigmas.atividadejdbc.application.repository.dao.MoviesDao;
import dev.welyab.bict.paradigmas.atividadejdbc.util.pagination.Page;
import dev.welyab.bict.paradigmas.atividadejdbc.util.pagination.PagedResult;
import dev.welyab.bict.paradigmas.atividadejdbc.core.entities.Movie;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MoviesDaoImpl implements MoviesDao {

    private final ConnectionFactory connectionFactory;

    public MoviesDaoImpl(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public Movie find(String id) {
        var list = find(List.of(id));
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public PagedResult<Movie> findAll(Page page) {
        var sql = """
                select
                    m.id,
                    m.name,
                    m.year,
                    m.imdb_score,
                    m.imdb_url
                from movies m
                offset ?
                limit ?
                """;
        try (var conn = connectionFactory.createConnection()) {
            try (var stm = conn.prepareStatement(sql)) {
                stm.setInt(1, page.getPageNumber() * page.getPageSize());
                stm.setInt(2, page.getPageSize());
                var rs = stm.executeQuery();
                List<Movie> movies = new ArrayList<>(100);
                while (rs.next()) {
                    var movie = new Movie();
                    movie.setId(rs.getString("id"));
                    movie.setName(rs.getString("name"));
                    movie.setYear(rs.getInt("year"));
                    movie.setImdbScore(rs.getBigDecimal("imdb_score"));
                    movie.setImdbUrl(rs.getString("imdb_url"));
                    movies.add(movie);
                }
                return new PagedResult<>(movies, page);
            }
        } catch (SQLException e) {
            throw new DaoException("Fail to insert movie into database", e);
        }
    }

    @Override
    public List<Movie> find(List<String> ids) {
        Preconditions.checkNotNull(ids, "ids");
        var sqlTemplate = """
                select
                    m.id,
                    m.name,
                    m.year,
                    m.imdb_score,
                    m.imdb_url
                from movies m
                where m.code in (%s)
                """;
        var sql = String.format(
                sqlTemplate,
                Joiner.on(',').join(ids.stream().map(e -> "?").iterator())
        );
        try (var conn = connectionFactory.createConnection()) {
            try (var stm = conn.prepareStatement(sql)) {
                for (var i = 0; i < ids.size(); i++) {
                    stm.setString(i + 1, ids.get(i));
                }
                var rs = stm.executeQuery();
                List<Movie> movies = new ArrayList<>(ids.size());
                while (rs.next()) {
                    var movie = new Movie();
                    movie.setId(rs.getString("id"));
                    movie.setName(rs.getString("name"));
                    movie.setYear(rs.getInt("year"));
                    movie.setImdbScore(rs.getBigDecimal("imdb_score"));
                    movie.setImdbUrl(rs.getString("imdb_url"));
                    movies.add(movie);
                }
                return movies;
            }
        } catch (SQLException e) {
            throw new DaoException(
                    String.format(
                            "Fail to find movies with ids '%s' from database",
                            Joiner.on(',').join(ids)
                    ),
                    e
            );
        }
    }

    public void save(Movie movie) {
        Preconditions.checkNotNull(movie, "movie");
        var sql = """
                insert into movies(id, name, year, imdb_score, imdb_url)
                            values( ?,    ?,    ?,          ?,        ?)
                """;
        try (var conn = connectionFactory.createConnection()) {
            try (var stm = conn.prepareStatement(sql)) {
                stm.setString(1, movie.getId());
                stm.setString(2, movie.getName());
                stm.setInt(3, movie.getYear());
                stm.setBigDecimal(4, movie.getImdbScore());
                stm.setString(5, movie.getImdbUrl());
                stm.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DaoException("Fail to insert movie into database", e);
        }
    }

    @Override
    public void update(Movie movie) {
        Preconditions.checkNotNull(movie, "movie");
        var sql = """
                update movies(name, release_year, release_month, imdb_url)
                set name = ?,
                    year = ?
                    imdb_score = ?
                    imdb_url = ?
                where code = ?
                """;
        try (var conn = connectionFactory.createConnection()) {
            try (var stm = conn.prepareStatement(sql)) {
                stm.setString(1, movie.getName());
                stm.setInt(2, movie.getYear());
                stm.setBigDecimal(3, movie.getImdbScore());
                stm.setString(4, movie.getImdbUrl());
                stm.setString(5, movie.getId());
                stm.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DaoException(String.format("Fail to update movie with id '%s' into database", movie.getId()), e);
        }
    }

    @Override
    public void remove(String id) {
        Preconditions.checkNotNull(id, "id");
        var sql = "delete from movies m where m.id = ?";
        try (var conn = connectionFactory.createConnection()) {
            try (var stt = conn.prepareStatement(sql)) {
                stt.setString(1, id);
                stt.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DaoException(String.format("Fail to delete movie with id '%s'", id), e);
        }
    }
}
