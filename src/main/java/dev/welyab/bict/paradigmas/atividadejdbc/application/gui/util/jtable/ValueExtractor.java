package dev.welyab.bict.paradigmas.atividadejdbc.application.gui.util.jtable;

public interface ValueExtractor {

    TableColumn getTableColumn();

    Object extractValue(Object source);
}
