/**
 * SimpleQuestion.java.
 */

package ql.astnodes.statements;

import ql.astnodes.LineNumber;
import ql.astnodes.expressions.literals.Identifier;
import ql.astnodes.visitors.FormAndStatementVisitor;

import ql.astnodes.types.Type;

public class SimpleQuestion extends Statement {

    private final Identifier identifier;
    private final String label;
    private final Type type;

    public SimpleQuestion(Identifier identifier, String label, Type type, LineNumber lineNumber) {
        super(lineNumber);
        this.identifier = identifier;
        this.label = label;
        this.type = type;
    }

    public Identifier getIdentifier() {
        return identifier;
    }

    public String getLabel() {
        return label;
    }

    public Type getType() {
        return type;
    }

    @Override
    public <T> T accept(FormAndStatementVisitor<T> visitor) {
        return visitor.visit(this);
    }
}