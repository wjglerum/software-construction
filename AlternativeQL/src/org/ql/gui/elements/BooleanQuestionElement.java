package org.ql.gui.elements;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import org.ql.ast.statement.Question;
import org.ql.gui.widgets.Widget;

public class BooleanQuestionElement extends QuestionElement {

    public BooleanQuestionElement(Question question, Widget widget) {
        super(mediator, question, widget);

        widget.addEventHandler(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Checkbox value set to: " + widget.getValue().getPlainValue());
                setValue(widget.getValue());
            }
        });
    }
}
