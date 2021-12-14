package dev.welyab.bict.paradigmas.atividadejdbc;

import dev.welyab.bict.paradigmas.atividadejdbc.application.Application;
import dev.welyab.bict.paradigmas.atividadejdbc.application.config.database.Database;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.UIManager;

import java.io.IOException;

public class MainGuiTest {

    private static final Logger logger = LogManager.getLogger(MainGuiTest.class);

    public static void main(String[] args) throws IOException, InterruptedException {
        // database parameters
        System.setProperty(Database.DATABASE_URL, "jdbc:hsqldb:mem:movies");
        System.setProperty(Database.DATABASE_USER, "sa");
        System.setProperty(Database.DATABASE_PASSWORD, "sa");

        try {
            for (var lookAndFeelInfo : UIManager.getInstalledLookAndFeels()) {
                if (lookAndFeelInfo.getClassName().contains("Nimbus")) {
                    UIManager.setLookAndFeel(lookAndFeelInfo.getClassName());
                }
            }
        } catch (Exception e) {
            logger.warn("Fail to set look and feel", e);
        }

        try (var application = new Application()) {
            logger.info("Application window is opening");
            application.launchGui();
            logger.info("Application window is closed");
        }
    }
}
