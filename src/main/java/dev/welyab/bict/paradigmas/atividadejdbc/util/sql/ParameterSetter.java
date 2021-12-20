package dev.welyab.bict.paradigmas.atividadejdbc.util.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@FunctionalInterface
public interface ParameterSetter {

    void setParameter(PreparedStatement rs, int parameterNumber) throws SQLException;
}
