package dev.welyab.bict.paradigmas.atividadejdbc.application.gui.util.jtable;

import com.google.common.collect.ComparisonChain;
import dev.welyab.bict.paradigmas.atividadejdbc.application.exception.ApplicationException;

import javax.swing.table.AbstractTableModel;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AnnotationTableModel<T> extends AbstractTableModel {

    private final Class<T> type;

    private List<WithConverterValueExtract> valueExtractors;
    private List<T> values = new ArrayList<>();

    public AnnotationTableModel(Class<T> type) {
        this.type = type;
        createValueExtractors();
    }

    private void createValueExtractors() {
        var tempExtractors = new ArrayList<WithConverterValueExtract>();
        tempExtractors.addAll(getValueExtractorFromFields(type.getFields()));
        tempExtractors.addAll(getValueExtractorFromFields(type.getMethods()));

        record Pair(Integer index, WithConverterValueExtract extractor) {
        }

        var sortedExtractors = new ArrayList<Pair>();
        for (int i = 0; i < tempExtractors.size(); i++) {
            sortedExtractors.add(new Pair(i, tempExtractors.get(i)));
        }

        valueExtractors = sortedExtractors
                .stream()
                .sorted((e1, e2) ->
                        ComparisonChain
                                .start()
                                .compare(e1.extractor.getTableColumn().position(), e2.extractor.getTableColumn().position())
                                .compare(e1.index, e2.index)
                                .result()
                )
                .map(e -> e.extractor)
                .collect(Collectors.toList());
    }

    public void setValues(List<T> values) {
        this.values = values;
        fireTableDataChanged();
    }

    @Override
    public String getColumnName(int column) {
        return valueExtractors.get(column).getTableColumn().name();
    }

    private List<WithConverterValueExtract> getValueExtractorFromFields(AccessibleObject[] accessibleObjects) {
        List<WithConverterValueExtract> extractors = new ArrayList<>();
        for (AccessibleObject accessibleObject : accessibleObjects) {
            if (accessibleObject.isAnnotationPresent(TableColumn.class)) {
                var tableColumn = accessibleObject.getAnnotation(TableColumn.class);
                extractors.add(new WithConverterValueExtract() {

                    ValueConverter valueConverter = createValueConverter(tableColumn.converter());

                    @Override
                    public ValueConverter getValueConverter() {
                        return valueConverter;
                    }

                    @Override
                    public TableColumn getTableColumn() {
                        return tableColumn;
                    }

                    @Override
                    public Object extractValue(Object source) {
                        try {
                            if (accessibleObject instanceof Field) {
                                return ((Field) accessibleObject).get(source);
                            } else if (accessibleObject instanceof Method) {
                                return ((Method) accessibleObject).invoke(source);
                            }
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            throw new ApplicationException("Failed to process value extraction", e);
                        }
                        throw new ApplicationException("Unexpected error");
                    }
                });
            }
        }
        return extractors;
    }

    private ValueConverter createValueConverter(Class<? extends ValueConverter> valueConverter) {
        try {
            return valueConverter.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new ApplicationException("Failed to create value converter", e);
        }
    }

    @Override
    public int getRowCount() {
        return values.size();
    }

    @Override
    public int getColumnCount() {
        return valueExtractors.size();
    }

    private interface WithConverterValueExtract extends ValueExtractor {
        ValueConverter getValueConverter();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        var rawData = values.get(rowIndex);
        WithConverterValueExtract valueExtractor = valueExtractors.get(columnIndex);
        var data = valueExtractor.extractValue(rawData);
        var converter = valueExtractor.getValueConverter();
        return converter.convert(data);
    }
}
