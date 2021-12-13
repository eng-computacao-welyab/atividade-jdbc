package dev.welyab.bict.paradigmas.atividadejdbc.application.repository.dao;

import java.sql.SQLException;

public class DaoException extends RuntimeException {

    public DaoException(String message) {
        this(message, null);
    }

    public DaoException(String message, SQLException cause) {
        super(message, cause);
    }
}
