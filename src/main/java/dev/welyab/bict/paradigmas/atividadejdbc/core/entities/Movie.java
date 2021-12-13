package dev.welyab.bict.paradigmas.atividadejdbc.core.entities;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.Objects;
import java.util.Optional;

public class Movie {

    private String id;
    private String name;
    private Integer year;
    private BigDecimal imdbScore;
    private String imdbUrl;

    public Movie() {
    }

    public Movie(String id) {
        this.id = id;
    }

    public Movie(String id, String name, Integer year, BigDecimal imdbScore, String imdbUrl) {
        this.id = id;
        this.name = name;
        this.year = year;
        this.imdbScore = imdbScore;
        this.imdbUrl = imdbUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public BigDecimal getImdbScore() {
        return imdbScore;
    }

    public void setImdbScore(BigDecimal imdbScore) {
        this.imdbScore = imdbScore;
    }

    public String getImdbUrl() {
        return imdbUrl;
    }

    public void setImdbUrl(String imdbUrl) {
        this.imdbUrl = imdbUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return Objects.equals(id, movie.id) && Objects.equals(name, movie.name) && Objects.equals(year, movie.year) && Objects.equals(imdbScore, movie.imdbScore) && Objects.equals(imdbUrl, movie.imdbUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, year, imdbScore, imdbUrl);
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", year=" + year +
                ", imdbScore=" + imdbScore +
                ", imdbUrl='" + imdbUrl + '\'' +
                '}';
    }
}
