package me.predatorray.jdbc;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

class ParameterList {

    private final List<?> parameterList;

    public ParameterList(List<?> parameterList) {
        this.parameterList = new LinkedList<Object>(parameterList);
    }

    public void accept(ParameterVisitor visitor) throws SQLException {
        for (Object parameter : parameterList) {
            visitor.visit(parameter);
        }
    }
}
