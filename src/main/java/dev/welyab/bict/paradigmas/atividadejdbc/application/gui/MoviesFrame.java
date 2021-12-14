package dev.welyab.bict.paradigmas.atividadejdbc.application.gui;

import dev.welyab.bict.paradigmas.atividadejdbc.core.services.MovieService;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicBorders;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

public class MoviesFrame extends JPanel {

    private final MovieService movieService;

    public MoviesFrame(MovieService movieService) {
        this.movieService = movieService;
        setLayout(new GridBagLayout());
        mountComponents();
    }

    private void mountComponents() {
        var c = new GridBagConstraints();

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        var lbName = new JLabel("Nome:");
        lbName.setHorizontalAlignment(SwingConstants.RIGHT);
        add(lbName, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 0;
        var tfName = new JTextField();
        add(tfName, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 1;
        var lbYear = new JLabel("Ano:");
        lbYear.setHorizontalAlignment(SwingConstants.RIGHT);
        add(lbYear, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 1;
        var tfYear = new JTextField();
        add(tfYear, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 2;
        var lbImdbScore = new JLabel("Nota (IMDB):");
        lbImdbScore.setHorizontalAlignment(SwingConstants.RIGHT);
        add(lbImdbScore, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 2;
        var tfImdbScore = new JTextField();
        add(tfImdbScore, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 3;
        var lbImdbLink = new JLabel("Link (IMDB):");
        lbImdbLink.setHorizontalAlignment(SwingConstants.RIGHT);
        add(lbImdbLink, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 3;
        var tfImdbLink = new JTextField();
        add(tfImdbLink, c);

        c.fill = GridBagConstraints.NONE;
        c.gridx = 0;
        c.gridy = 4;
        var btSave = new JButton("Salvar");
        add(btSave, c);

        c.fill = GridBagConstraints.NONE;
        c.gridx = 1;
        c.gridy = 4;
        var btClear = new JButton("Limpar");
        add(btClear, c);
    }

    public static void main(String[] args) {
        var moviesFrame = new MoviesFrame(null);

        var frame = new JFrame();
        frame.getContentPane().add(moviesFrame);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
