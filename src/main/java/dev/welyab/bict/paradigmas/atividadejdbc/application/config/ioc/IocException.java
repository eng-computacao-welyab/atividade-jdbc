package dev.welyab.bict.paradigmas.atividadejdbc.application.config.ioc;

public class IocException extends RuntimeException {
    public IocException(String message) {
        super(message);
    }

    public IocException(String message, Throwable cause) {
        super(message, cause);
    }
}
