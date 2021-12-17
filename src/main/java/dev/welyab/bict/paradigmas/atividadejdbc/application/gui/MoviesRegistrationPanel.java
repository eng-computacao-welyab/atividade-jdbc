package dev.welyab.bict.paradigmas.atividadejdbc.application.gui;

import dev.welyab.bict.paradigmas.atividadejdbc.core.entities.Movie;
import dev.welyab.bict.paradigmas.atividadejdbc.core.services.MovieService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;

public class MoviesRegistrationPanel extends JPanel {

    private static final Logger logger = LogManager.getLogger(MoviesRegistrationPanel.class);

    private final MovieService movieService;

    private JTextField tfName;
    private JTextField tfYear;
    private JTextField tfImdbScore;
    private JTextField tfImdbLink;

    public MoviesRegistrationPanel(MovieService movieService) {
        this.movieService = movieService;
        setLayout(new GridBagLayout());
        mountComponents();
    }

    private void mountComponents() {
        var c = new GridBagConstraints();

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 4;
        var lbName = new JLabel("Nome:");
        lbName.setHorizontalAlignment(SwingConstants.RIGHT);
        add(lbName, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 0;
        c.weightx = 16;
        tfName = new JTextField();
        tfName.addActionListener(this::saveAction);
        add(tfName, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 4;
        var lbYear = new JLabel("Ano:");
        lbYear.setHorizontalAlignment(SwingConstants.RIGHT);

        add(lbYear, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 1;
        c.weightx = 16;
        tfYear = new JTextField();
        tfYear.addActionListener(this::saveAction);
        add(tfYear, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 2;
        c.weightx = 4;
        var lbImdbScore = new JLabel("Nota (IMDB):");
        lbImdbScore.setHorizontalAlignment(SwingConstants.RIGHT);
        add(lbImdbScore, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 2;
        c.weightx = 16;
        tfImdbScore = new JTextField();
        tfImdbScore.addActionListener(this::saveAction);
        add(tfImdbScore, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 3;
        c.weightx = 4;
        var lbImdbLink = new JLabel("Link (IMDB):");
        lbImdbLink.setHorizontalAlignment(SwingConstants.RIGHT);
        add(lbImdbLink, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 3;
        c.weightx = 16;
        tfImdbLink = new JTextField();
        tfImdbLink.addActionListener(this::saveAction);
        add(tfImdbLink, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 4;
        c.weightx = 10;
        var btSave = new JButton("Salvar");
        btSave.setMnemonic(KeyEvent.VK_S);
        btSave.addActionListener(this::saveAction);
        add(btSave, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 4;
        c.weightx = 10;
        var btClear = new JButton("Limpar");
        btClear.setMnemonic(KeyEvent.VK_L);
        btClear.addActionListener(this::clearAction);
        add(btClear, c);
    }

    public void saveAction(ActionEvent actionListener) {
        SwingUtilities.invokeLater(() -> {
            movieService.save(getMovie());
            clearAction(actionListener);
            JOptionPane.showMessageDialog(
                    this,
                    "Filme salvo com sucesso",
                    "Sucesso",
                    JOptionPane.INFORMATION_MESSAGE
            );
        });
    }

    private Movie getMovie() {
        var movie = new Movie();
        movie.setName(tfName.getText());
        movie.setYear(Integer.valueOf(tfYear.getText()));
        movie.setImdbScore(new BigDecimal(tfImdbScore.getText()));
        movie.setImdbUrl(tfImdbLink.getText());
        return movie;
    }

    public void clearAction(ActionEvent actionEvent) {
        SwingUtilities.invokeLater(() -> {
            tfName.setText("");
            tfYear.setText("");
            tfImdbScore.setText("");
            tfImdbLink.setText("");
        });
    }
}
