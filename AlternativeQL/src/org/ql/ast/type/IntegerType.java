package org.ql.ast.type;

public class IntegerType extends NumberType {
    @Override
    public String toString() {
        return "integer";
    }

    @Override
    public boolean isCompatibleWith(Type type) {
        return type.isCompatibleWith(this);
    }

    @Override
    public boolean isCompatibleWith(IntegerType type) {
        return true;
    }
}
