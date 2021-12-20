package dev.welyab.bict.paradigmas.atividadejdbc.util.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QueryHelper {

    private QueryBuilder builder;

    private boolean distinct = false;
    private List<String> columns = new ArrayList<>();
    private List<String> tables = new ArrayList<>();
    private List<String> conditions = new ArrayList<>();
    private List<ParameterSetter> parameterSetters = new ArrayList<>();
    private List<String> sorts = new ArrayList<>();

    private QueryHelper(QueryBuilder builder) {
        this.builder = builder;
    }

    public QueryHelper distinct() {
        distinct = true;
        return this;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public static QueryHelper select() {
        return new QueryHelper(new SelectQueryBuilder());
    }

    public QueryHelper addTable(String table) {
        tables.add(table);
        return this;
    }

    public List<String> getTables() {
        return tables;
    }

    public QueryHelper addColumns(List<String> columns) {
        this.columns.addAll(columns);
        return this;
    }

    public List<String> getColumns() {
        return columns;
    }

    public QueryHelper addCondition(String condition) {
        conditions.add(condition);
        return this;
    }

    public QueryHelper addCondition(String condition, ParameterSetter parameterSetter) {
        conditions.add(condition);
        parameterSetters.add(parameterSetter);
        return this;
    }

    public List<String> getConditions() {
        return conditions;
    }

    public QueryHelper addSortAsc(String s) {
        sorts.add(String.format("%s asc", s));
        return this;
    }

    public List<String> getSorts() {
        return sorts;
    }

    public String getSql() {
        return builder.getSql(this);
    }

    public void setParameters(PreparedStatement stt) throws SQLException {
        for (int i = 0; i < parameterSetters.size(); i++) {
            var setter = parameterSetters.get(i);
            setter.setParameter(stt, i + 1);
        }
    }
}
