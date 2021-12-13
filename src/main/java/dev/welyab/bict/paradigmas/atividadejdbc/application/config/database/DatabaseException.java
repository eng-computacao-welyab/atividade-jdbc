package dev.welyab.bict.paradigmas.atividadejdbc.application.config.database;

public class DatabaseException extends RuntimeException{

    public DatabaseException() {
    }

    public DatabaseException(String message) {
        super(message);
    }

    public DatabaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
