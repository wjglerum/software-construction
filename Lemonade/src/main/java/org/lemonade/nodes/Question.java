package org.lemonade.nodes;

import org.lemonade.nodes.expressions.Type;
import org.lemonade.visitors.BlockVisitor;
import org.lemonade.visitors.ExpressionVisitor;

public class Question extends Block {
    private String identifier;
    private String label;
    private Type type;

    public Question(String identifier, String label, Type type) {
        super();
        this.identifier = identifier;
        this.label = label;
        this.type = type;
    }

    public <T> T accept(BlockVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
