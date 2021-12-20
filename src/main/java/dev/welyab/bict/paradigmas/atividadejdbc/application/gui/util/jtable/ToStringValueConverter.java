package dev.welyab.bict.paradigmas.atividadejdbc.application.gui.util.jtable;

import java.util.Optional;

public class ToStringValueConverter implements ValueConverter {

    @Override
    public String convert(Object value) {
        return Optional.ofNullable(value).map(Object::toString).orElse("");
    }
}
