package dev.welyab.bict.paradigmas.atividadejdbc.application.repository.dao.impl;

import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import dev.welyab.bict.paradigmas.atividadejdbc.application.Application;
import dev.welyab.bict.paradigmas.atividadejdbc.application.config.database.ConnectionFactory;
import dev.welyab.bict.paradigmas.atividadejdbc.application.repository.dao.DaoException;
import dev.welyab.bict.paradigmas.atividadejdbc.application.repository.dao.MoviesDao;
import dev.welyab.bict.paradigmas.atividadejdbc.util.pagination.Page;
import dev.welyab.bict.paradigmas.atividadejdbc.util.pagination.PagedResult;
import dev.welyab.bict.paradigmas.atividadejdbc.core.entities.Movie;
import dev.welyab.bict.paradigmas.atividadejdbc.util.sql.QueryHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MoviesDaoImpl implements MoviesDao {

    private static final Logger logger = LogManager.getLogger(MoviesDaoImpl.class);

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
                    m.imdb_url,
                    m.registration_date
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
                    var movie = resultSetToMovie(rs);
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
                    m.imdb_url,
                    m.registration_date
                from movies m
                where m.code in (%s)
                order by m.name
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
                    movies.add(resultSetToMovie(rs));
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
                insert into movies(id, name, year, imdb_score, imdb_url, registration_date)
                            values( ?,    ?,    ?,          ?,        ?,                 ?)
                """;
        try (var conn = connectionFactory.createConnection()) {
            try (var stm = conn.prepareStatement(sql)) {
                stm.setString(1, movie.getId());
                stm.setString(2, movie.getName());
                stm.setInt(3, movie.getYear());
                stm.setBigDecimal(4, movie.getImdbScore());
                stm.setString(5, movie.getImdbUrl());
                stm.setDate(6, Date.valueOf(movie.getRegistrationDate()));
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
                    imdb_url = ?,
                    registration_date = ?
                where code = ?
                """;
        try (var conn = connectionFactory.createConnection()) {
            try (var stm = conn.prepareStatement(sql)) {
                stm.setString(1, movie.getName());
                stm.setInt(2, movie.getYear());
                stm.setBigDecimal(3, movie.getImdbScore());
                stm.setString(4, movie.getImdbUrl());
                stm.setString(5, movie.getId());
                stm.setDate(6, Date.valueOf(movie.getRegistrationDate()));
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

    @Override
    public List<Movie> findAllLike(Movie movie) {
        if (movie == null) {
            return new LinkedList<>();
        }

        var query = QueryHelper
                .select()
                .addTable("movies m")
                .addColumns(List.of(
                        "m.id",
                        "m.name",
                        "m.year",
                        "m.imdb_url",
                        "m.imdb_score",
                        "m.registration_date"
                ));

        if (!Strings.isNullOrEmpty(movie.getName())) {
            query.addCondition(
                    "m.name like ?",
                    (rs, num) -> rs.setString(num, String.format("%%%s%%", movie.getName()))
            );
        }

        if (movie.getYear() != null) {
            query.addCondition(
                    "m.year = ?",
                    (rs, num) -> rs.setInt(num, movie.getYear())
            );
        }

        if (movie.getImdbScore() != null) {
            query.addCondition(
                    "m.imdb_score = ?",
                    (rs, num) -> rs.setBigDecimal(num, movie.getImdbScore())
            );
        }

        if (!Strings.isNullOrEmpty(movie.getImdbUrl())) {
            query.addCondition(
                    "m.imdb_url = ?",
                    (rs, num) -> rs.setString(num, movie.getImdbUrl())
            );
        }

        query.addSortAsc("m.name");

        try (var conn = connectionFactory.createConnection()) {
            String sql = query.getSql();
            logger.debug(sql);
            try (var stt = conn.prepareStatement(sql)) {
                query.setParameters(stt);
                var rs = stt.executeQuery();
                var movies = new ArrayList<Movie>();
                while (rs.next()) {
                    movies.add(resultSetToMovie(rs));
                }
                return movies;
            }
        } catch (SQLException e) {
            throw new DaoException("Fail to select movies", e);
        }
    }

    private Movie resultSetToMovie(ResultSet rs) throws SQLException {
        var movie = new Movie();
        movie.setId(rs.getString("id"));
        movie.setName(rs.getString("name"));
        movie.setYear(rs.getInt("year"));
        movie.setImdbScore(rs.getBigDecimal("imdb_score"));
        movie.setImdbUrl(rs.getString("imdb_url"));
        movie.setRegistrationDate(rs.getDate("registration_date").toLocalDate());
        return movie;
    }
}
