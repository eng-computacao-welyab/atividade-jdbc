package dev.welyab.bict.paradigmas.atividadejdbc.application.gui.util.jtable;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class LocalDateValueConverter implements ValueConverter {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd");

    @Override
    public String convert(Object value) {
        return Optional.ofNullable(value)
                .map(e -> (LocalDate) e)
                .map(FORMATTER::format)
                .orElse("");
    }
}
