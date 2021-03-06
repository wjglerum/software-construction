package org.qls.typechecker.issues.errors;

import org.ql.ast.Node;
import org.ql.typechecker.issues.Issue;
import org.qls.ast.page.CustomWidgetQuestion;

public class InconsistentWidgetWithQuestionType extends Issue {

    private final CustomWidgetQuestion question;

    public InconsistentWidgetWithQuestionType(CustomWidgetQuestion question) {
        this.question = question;
    }

    @Override
    public String getMessage() {
        return "Widget for QLS question " + question.getId() + " doesn't support QL question type.";
    }

    @Override
    public Node getNode() {
        return question;
    }
}
