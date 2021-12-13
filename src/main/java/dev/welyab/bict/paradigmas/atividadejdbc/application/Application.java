package dev.welyab.bict.paradigmas.atividadejdbc.application;

import dev.welyab.bict.paradigmas.atividadejdbc.application.config.database.Database;
import dev.welyab.bict.paradigmas.atividadejdbc.application.config.ioc.Ioc;
import dev.welyab.bict.paradigmas.atividadejdbc.application.exception.ApplicationException;
import dev.welyab.bict.paradigmas.atividadejdbc.application.repository.dao.impl.MoviesDaoImpl;
import dev.welyab.bict.paradigmas.atividadejdbc.application.services.MovieServiceImpl;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static dev.welyab.bict.paradigmas.atividadejdbc.application.Application.Components.CONNECTION_FACTORY;
import static dev.welyab.bict.paradigmas.atividadejdbc.application.Application.Components.MOVIES_DAO;
import static dev.welyab.bict.paradigmas.atividadejdbc.application.Application.Components.MOVIES_SERVICE;
import static dev.welyab.bict.paradigmas.atividadejdbc.application.config.ioc.Ioc.registerSingleInstance;

public class Application implements Closeable {

    private final Database database;

    public static class Components {
        private Components() {
        }

        public static final String CONNECTION_FACTORY = "Application.Components.CONNECTION_FACTORY";
        public static final String MOVIES_DAO = "Application.Components.MOVIES_DAO";
        public static final String MOVIES_SERVICE = "Application.Components.MOVIES_SERVICE";
    }

    public Application() {
        database = new Database();
        registerComponents();

    }

    private void registerComponents() {
        registerSingleInstance(
                CONNECTION_FACTORY,
                () -> database
        );
        registerSingleInstance(
                MOVIES_DAO,
                () -> new MoviesDaoImpl(Ioc.instance(CONNECTION_FACTORY))
        );
        registerSingleInstance(
                MOVIES_SERVICE,
                () -> new MovieServiceImpl(
                        Ioc.instance(MOVIES_DAO)
                )
        );
    }

    @Override
    public void close() throws IOException {
        List<Exception> closeErrorList = new ArrayList<>();
        try {
            database.close();
        } catch (Exception e) {
            closeErrorList.add(e);
        }
        try {
            Ioc.clear();
        } catch (Exception e) {
            closeErrorList.add(e);
        }
        if (!closeErrorList.isEmpty()) {
            var exception = new ApplicationException("Fail to  properly close application");
            closeErrorList.forEach(exception::addSuppressed);
            throw exception;
        }
    }
}
