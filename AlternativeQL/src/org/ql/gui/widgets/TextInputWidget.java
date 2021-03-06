package org.ql.gui.widgets;


import org.ql.ast.statement.Question;
import org.ql.evaluator.value.StringValue;
import org.ql.evaluator.value.Value;
import org.ql.gui.ValueReviser;

public class TextInputWidget extends InputWidget {

    public TextInputWidget(ValueReviser valueReviser, Question question) {
        super(valueReviser, question);
    }

    @Override
    protected Value value(String textFieldText) {
        return new StringValue(textFieldText);
    }
}
