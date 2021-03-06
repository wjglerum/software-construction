package com.matthewchapman.ql.environment.values;

/**
 * Created by matt on 18/03/2017.
 */

public class BooleanValue extends Value {

    private final boolean value;

    public BooleanValue(boolean input) {
        this.value = input;
    }

    @Override
    public Boolean getValue() {
        return value;
    }

    @Override
    public String toString() {
        return Boolean.toString(value);
    }

    @Override
    public Value equalTo(BooleanValue value) {
        return new BooleanValue(this.value == value.getValue());
    }

    @Override
    public Value equalTo(Value value) {
        return value.equalTo(this);
    }

    @Override
    public Value notEqualTo(BooleanValue value) {
        return new BooleanValue(this.value != value.getValue());
    }

    @Override
    public Value notEqualTo(Value value) {
        return value.notEqualTo(this);
    }

    @Override
    public Value logicalAnd(BooleanValue value) {
        return new BooleanValue(this.value && value.getValue());
    }

    @Override
    public Value logicalAnd(Value value) {
        return value.logicalAnd(this);
    }

    @Override
    public Value logicalOr(BooleanValue value) {
        return new BooleanValue(this.value || value.getValue());
    }

    @Override
    public Value logicalOr(Value value) {
        return value.logicalOr(this);
    }

    @Override
    public Value negate(BooleanValue value) {
        return new BooleanValue(!value.getValue());
    }

    @Override
    public Value negate(Value value) {
        return value.negate(this);
    }
}
