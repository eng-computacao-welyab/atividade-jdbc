package dev.welyab.bict.paradigmas.atividadejdbc;

import javax.swing.table.AbstractTableModel;
import java.lang.reflect.ParameterizedType;

public class GenericTableModel<T> extends AbstractTableModel {

    public Class<T> getType() {

        return null;
    }

    @Override
    public int getRowCount() {
        return 0;
    }

    @Override
    public int getColumnCount() {
        return 0;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return null;
    }

    static class Sampleclass {
    }

    public static void main(String[] args) {
        var model = new GenericTableModel<Sampleclass>();
        System.out.println(model.getClass().getGenericSuperclass() instanceof ParameterizedType);
    }
}
