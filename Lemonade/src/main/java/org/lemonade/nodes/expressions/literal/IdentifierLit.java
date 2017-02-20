package org.lemonade.nodes.expressions.literal;

import org.lemonade.nodes.expressions.Expression;
import org.lemonade.nodes.expressions.Literal;
import org.lemonade.visitors.ExpressionVisitor;

/**
 *
 */
public class IdentifierLit extends Literal {
    private String value;

    public IdentifierLit(String value) {
        this.value = value;
    }

    public <T> T accept(ExpressionVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return value;
    }
}