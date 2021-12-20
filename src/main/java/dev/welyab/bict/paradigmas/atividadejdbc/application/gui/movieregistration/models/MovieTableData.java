package dev.welyab.bict.paradigmas.atividadejdbc.application.gui.movieregistration.models;

import dev.welyab.bict.paradigmas.atividadejdbc.application.gui.util.jtable.LocalDateValueConverter;
import dev.welyab.bict.paradigmas.atividadejdbc.application.gui.util.jtable.TableColumn;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface MovieTableData {

    @TableColumn(name = "BICT", position = -1)
    String getBict();

    @TableColumn(name = "Nome", position = 999)
    String getName();

    @TableColumn(name = "Ano", position = 1)
    Integer getYear();

    @TableColumn(name = "Nota (IMDB)", position = 79)
    BigDecimal getScore();

    @TableColumn(name = "URL (IMDB)")
    String getUrlImdb();


    @TableColumn(name = "Data registro", converter = LocalDateValueConverter.class)
    LocalDate getRegistrationDate();
}
