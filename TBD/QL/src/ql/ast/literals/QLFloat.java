package ql.ast.literals;

import ql.values.FloatValue;
import ql.visistor.interfaces.ExpressionVisitor;

/**
 * Created by Erik on 7-2-2017.
 */
public class QLFloat extends QLLiteral {
    private final float qlFloat;

    public QLFloat(float qlFloat, int rowNumber) {
        super(rowNumber);
        this.qlFloat = qlFloat;
    }

    public float getValue() {
        return qlFloat;
    }

    @Override
    public String toString() {
        return String.valueOf(qlFloat);
    }

    public FloatValue toValue() {
        return new FloatValue(qlFloat);
    }

    public <T> T accept(ExpressionVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
