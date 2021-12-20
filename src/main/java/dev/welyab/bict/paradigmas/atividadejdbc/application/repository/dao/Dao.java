package dev.welyab.bict.paradigmas.atividadejdbc.application.repository.dao;

import dev.welyab.bict.paradigmas.atividadejdbc.util.pagination.Page;
import dev.welyab.bict.paradigmas.atividadejdbc.util.pagination.PagedResult;

import java.util.List;

public interface Dao<Value, Id> {

    void save(Value value);

    Value find(Id id);

    List<Value> find(List<Id> ids);

    PagedResult<Value> findAll(Page page);

    void update(Value value);

    void remove(Id id);
}
