package dev.welyab.bict.paradigmas.atividadejdbc.application.gui;

import dev.welyab.bict.paradigmas.atividadejdbc.application.Application;
import dev.welyab.bict.paradigmas.atividadejdbc.application.config.ioc.Ioc;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import java.awt.BorderLayout;
import java.awt.event.WindowEvent;

public class MainFrame extends JFrame {

    public MainFrame() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        mountMenu();

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(Ioc.<JComponent>instance(Application.Components.MOVIE_FRAME));
    }

    private void mountMenu() {
        var menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        var fileMenu = new JMenu("Arquivo");
        menuBar.add(fileMenu);
        var exitItem = new JMenuItem("Sair");
        fileMenu.add(exitItem);
        exitItem.addActionListener(e -> {
            MainFrame.this.dispose();
            MainFrame.this.dispatchEvent(new WindowEvent(MainFrame.this, WindowEvent.WINDOW_CLOSING));
        });
    }
}
