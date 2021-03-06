/*
 * Software Construction - University of Amsterdam
 *
 * ./src/ql/gui/evaluation/values/StringValue.java.
 *
 * Gerben van der Huizen    -   10460748
 * Vincent Erich            -   10384081
 *
 * March, 2017
 */

package ql.gui.formenvironment.values;

public class StringValue extends Value {

    private final String value;

    public StringValue(String value) {
        this.value = value;
    }

    @Override
    public Value eq(Value valueArgument) {
        return new BooleanValue(value.equals(valueArgument.getValue()));
    }

    @Override
    public Value neq(Value valueArgument) {
        return new BooleanValue(!value.equals(valueArgument.getValue()));
    }

    @Override
    public Value gt(Value valueArgument) {
        return new BooleanValue(value.length() > ((String) valueArgument.getValue()).length());
    }

    @Override
    public Value gteq(Value valueArgument) {
        return new BooleanValue(value.length() >= ((String) valueArgument.getValue()).length());
    }

    @Override
    public Value lt(Value valueArgument) {
        return new BooleanValue(value.length() < ((String) valueArgument.getValue()).length());
    }

    @Override
    public Value lteq(Value valueArgument) {
        return new BooleanValue(value.length() <= ((String) valueArgument.getValue()).length());
    }

    @Override
    public String getValue() {
        return value;
    }
}
