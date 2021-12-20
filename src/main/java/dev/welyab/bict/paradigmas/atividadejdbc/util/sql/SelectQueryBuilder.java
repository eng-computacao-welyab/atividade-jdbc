package dev.welyab.bict.paradigmas.atividadejdbc.util.sql;

public class SelectQueryBuilder implements QueryBuilder {

    private static final String NEWLINE = String.format("%n");

    private String tabs = "";

    @Override
    public String getSql(QueryHelper queryHelper) {
        var builder = new StringBuilder();

        // select columns area
        builder.append(tabs).append("select");
        if (queryHelper.isDistinct()) {
            builder.append(" distinct");
        }
        builder.append(NEWLINE);

        addTab();
        for (int i = 0; i < queryHelper.getColumns().size(); i++) {
            String column = queryHelper.getColumns().get(i);
            builder.append(tabs).append(column);

            if (i < queryHelper.getColumns().size() - 1) {
                builder.append(",");
            }
            builder.append(NEWLINE);
        }
        removeTab();

        // from area, with joins if any
        builder.append(tabs).append("from").append(NEWLINE);
        addTab();
        for (int i = 0; i < queryHelper.getTables().size(); i++) {
            String table = queryHelper.getTables().get(i);
            builder.append(tabs).append(table);

            if (i < queryHelper.getTables().size() - 1) {
                builder.append(",");
            }
            builder.append(NEWLINE);
        }
        removeTab();

        // where
        if (!queryHelper.getConditions().isEmpty()) {
            builder.append(tabs).append("where").append(NEWLINE);
            addTab();

            for (int i = 0; i < queryHelper.getConditions().size(); i++) {
                var condition = queryHelper.getConditions().get(i);
                builder.append(tabs);
                if (i > 0) {
                    builder.append("and ");
                }
                builder.append(condition).append(NEWLINE);
            }

            removeTab();
        }

        // sort by
        if (!queryHelper.getSorts().isEmpty()) {
            builder.append(tabs).append("order by").append(NEWLINE);
            addTab();

            for (int i = 0; i < queryHelper.getSorts().size(); i++) {
                var sort = queryHelper.getSorts().get(i);
                builder.append(tabs).append(sort);
                if (i < queryHelper.getSorts().size() - 1) {
                    builder.append(",");
                }
                builder.append(NEWLINE);
            }

            removeTab();
        }

        return builder.toString();
    }

    private void addTab() {
        tabs = tabs + "\t";
    }

    private void removeTab() {
        tabs = tabs.substring(0, tabs.length() - 1);
    }
}
