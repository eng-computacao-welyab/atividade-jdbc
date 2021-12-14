package dev.welyab.bict.paradigmas.atividadejdbc.application;

import dev.welyab.bict.paradigmas.atividadejdbc.application.config.database.Database;
import dev.welyab.bict.paradigmas.atividadejdbc.application.config.ioc.Ioc;
import dev.welyab.bict.paradigmas.atividadejdbc.application.exception.ApplicationException;
import dev.welyab.bict.paradigmas.atividadejdbc.application.gui.MainFrame;
import dev.welyab.bict.paradigmas.atividadejdbc.application.gui.MoviesFrame;
import dev.welyab.bict.paradigmas.atividadejdbc.application.repository.dao.impl.MoviesDaoImpl;
import dev.welyab.bict.paradigmas.atividadejdbc.application.services.MovieServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import static dev.welyab.bict.paradigmas.atividadejdbc.application.Application.Components.CONNECTION_FACTORY;
import static dev.welyab.bict.paradigmas.atividadejdbc.application.Application.Components.MAIN_FRAME;
import static dev.welyab.bict.paradigmas.atividadejdbc.application.Application.Components.MOVIES_DAO;
import static dev.welyab.bict.paradigmas.atividadejdbc.application.Application.Components.MOVIES_SERVICE;
import static dev.welyab.bict.paradigmas.atividadejdbc.application.Application.Components.MOVIE_FRAME;
import static dev.welyab.bict.paradigmas.atividadejdbc.application.config.ioc.Ioc.instance;
import static dev.welyab.bict.paradigmas.atividadejdbc.application.config.ioc.Ioc.registerFactoryInstance;
import static dev.welyab.bict.paradigmas.atividadejdbc.application.config.ioc.Ioc.registerSingleInstance;

public class Application implements Closeable {

    private static final Logger logger = LogManager.getLogger(Application.class);

    private final Database database;

    public static class Components {
        private Components() {
        }

        public static final String CONNECTION_FACTORY = "Application.Components.CONNECTION_FACTORY";
        public static final String MOVIES_DAO = "Application.Components.MOVIES_DAO";
        public static final String MOVIES_SERVICE = "Application.Components.MOVIES_SERVICE";

        // GUI
        public static final String MAIN_FRAME = "Application.Components.MAIN_FRAME";
        public static final String MOVIE_FRAME = "Application.Components.MOVIE_FRAME";
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
        registerFactoryInstance(
                MAIN_FRAME,
                () -> new MainFrame()
        );
        registerFactoryInstance(
                MOVIE_FRAME,
                () -> new MoviesFrame(
                        instance(MOVIES_SERVICE)
                )
        );
    }

    public void launchGui() throws InterruptedException {
        var frame = instance(JFrame.class, Application.Components.MAIN_FRAME);
        frame.setTitle("Atividade de Paradigmas de Programação - Welyab Paula");
        logger.info("Application window is all set");
        SwingUtilities.invokeLater(() -> frame.setVisible(true));
        final var latch = new CountDownLatch(1);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                latch.countDown();
            }
        });
        latch.await();
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
