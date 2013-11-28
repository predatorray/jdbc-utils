package me.predatorray.jdbc;

public interface ParameterVisitor {

    void visit(Integer i);

    void visit(Long l);

    void visit(String s);

    void visit(Character c);

    void visit(Short s);
}
