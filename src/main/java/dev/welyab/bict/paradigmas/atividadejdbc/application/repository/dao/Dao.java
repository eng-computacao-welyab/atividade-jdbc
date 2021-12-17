package dev.welyab.bict.paradigmas.atividadejdbc.application.repository.dao;

import dev.welyab.bict.paradigmas.atividadejdbc.util.pagination.Page;
import dev.welyab.bict.paradigmas.atividadejdbc.util.pagination.PagedResult;
import dev.welyab.bict.paradigmas.atividadejdbc.core.entities.Movie;

import java.util.List;

public interface Dao<Value, Id> {

    void save(Value value);

    Value find(Id id);

    List<Movie> find(List<String> ids);

    PagedResult<Value> findAll(Page page);

    void update(Value value);

    void remove(Id id);
}
