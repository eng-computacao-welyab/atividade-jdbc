package dev.welyab.bict.paradigmas.atividadejdbc.application.gui;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import java.awt.event.WindowEvent;

public class MainFrame extends JFrame {

    public MainFrame() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        mountMenu();
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
