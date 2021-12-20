package dev.welyab.bict.paradigmas.atividadejdbc.application.gui.movieregistration;

import com.google.common.base.Strings;
import dev.welyab.bict.paradigmas.atividadejdbc.core.entities.Movie;
import dev.welyab.bict.paradigmas.atividadejdbc.core.services.MovieService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

public class MoviesRegistrationPanel extends JPanel {

    private static final Logger logger = LogManager.getLogger(MoviesRegistrationPanel.class);

    private static final String[] COLUMN_NAMES = new String[]{"Nome", "Ano", "Nota - IMDB", "URL - IMDB"};

    private final MovieService movieService;

    private JTextField tfName;
    private JTextField tfYear;
    private JTextField tfImdbScore;
    private JTextField tfImdbLink;
    private JTable jtResults;

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
        c.gridwidth = 2;
        var lbName = new JLabel("Nome:");
        lbName.setHorizontalAlignment(SwingConstants.RIGHT);
        add(lbName, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 2;
        c.gridy = 0;
        c.gridwidth = 6;
        tfName = new JTextField();
        tfName.addActionListener(this::saveAction);
        add(tfName, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 2;
        var lbYear = new JLabel("Ano:");
        lbYear.setHorizontalAlignment(SwingConstants.RIGHT);
        add(lbYear, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 2;
        c.gridy = 1;
        c.gridwidth = 2;
        tfYear = new JTextField();
        tfYear.addActionListener(this::saveAction);
        add(tfYear, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 2;
        var lbImdbScore = new JLabel("Nota (IMDB):");
        lbImdbScore.setHorizontalAlignment(SwingConstants.RIGHT);
        add(lbImdbScore, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 2;
        c.gridy = 2;
        c.gridwidth = 2;
        tfImdbScore = new JTextField();
        tfImdbScore.addActionListener(this::saveAction);
        add(tfImdbScore, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 2;
        var lbImdbLink = new JLabel("Link (IMDB):");
        lbImdbLink.setHorizontalAlignment(SwingConstants.RIGHT);
        add(lbImdbLink, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 2;
        c.gridy = 3;
        c.gridwidth = 6;
        tfImdbLink = new JTextField();
        tfImdbLink.addActionListener(this::saveAction);
        add(tfImdbLink, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 2;
        c.gridy = 4;
        c.gridwidth = 2;
        var btSave = new JButton("Salvar");
        btSave.setMnemonic(KeyEvent.VK_S);
        btSave.addActionListener(this::saveAction);
        add(btSave, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 4;
        c.gridy = 4;
        c.gridwidth = 2;
        var btClear = new JButton("Limpar");
        btClear.setMnemonic(KeyEvent.VK_L);
        btClear.addActionListener(this::clearAction);
        add(btClear, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 6;
        c.gridy = 4;
        c.gridwidth = 2;
        var btSearch = new JButton("Pesquisar");
        btSearch.setMnemonic(KeyEvent.VK_L);
        btSearch.addActionListener(this::searchAction);
        add(btSearch, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 5;
        c.gridwidth = 8;
        c.gridheight = 11;
        var resultsScroll = new JScrollPane();
        jtResults = new JTable();
        jtResults.setModel(new DefaultTableModel(
                listToData(Collections.emptyList()),
                getColumnNames()
        ));
        resultsScroll.setViewportView(jtResults);
        add(resultsScroll, c);
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
        if (!Strings.isNullOrEmpty(tfYear.getText())) {
            movie.setYear(Integer.valueOf(tfYear.getText()));
        }
        if (!Strings.isNullOrEmpty(tfImdbScore.getText())) {
            movie.setImdbScore(new BigDecimal(tfImdbScore.getText()));
        }
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

    public void searchAction(ActionEvent actionEvent) {
        List<Movie> movies = movieService.findAllLike(getMovie());
        jtResults.setModel(new DefaultTableModel(listToData(movies), getColumnNames()));
    }

    private Vector<Vector<String>> listToData(List<Movie> movies) {
        var data = new Vector<Vector<String>>();
        for (Movie movie : movies) {
            var row = new Vector<String>();
            row.add(movie.getName());
            row.add(movie.getYear().toString());
            row.add(movie.getImdbScore().toString());
            row.add(movie.getImdbUrl());
            data.add(row);
        }
        return data;
    }

    private Vector<String> getColumnNames() {
        var columns = new Vector<String>();
        for (String columnName : COLUMN_NAMES) {
            columns.add(columnName);
        }
        return columns;
    }
}
