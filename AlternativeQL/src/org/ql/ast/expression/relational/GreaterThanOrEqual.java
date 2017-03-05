package org.ql.ast.expression.relational;

import org.ql.ast.Expression;

public class GreaterThanOrEqual implements Expression {
    private Expression left;
    private Expression right;

    public GreaterThanOrEqual(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    public Expression getLeft() {
        return left;
    }

    public Expression getRight() {
        return right;
    }

    @Override
    public String toString() {
        return "(" + left + ">=" + right + ")";
    }
}