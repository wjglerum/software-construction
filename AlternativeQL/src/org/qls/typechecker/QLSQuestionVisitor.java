package org.qls.typechecker;

import org.ql.ast.identifier.Identifier;
import org.ql.ast.identifier.IdentifierSet;
import org.ql.ast.type.Type;
import org.ql.typechecker.SymbolTable;
import org.ql.typechecker.issues.IssuesStorage;
import org.qls.ast.StyleSheet;
import org.qls.ast.page.*;
import org.qls.typechecker.issues.errors.UnusedQuestion;
import org.qls.typechecker.issues.errors.DuplicateQuestion;
import org.qls.typechecker.issues.errors.UndefinedQuestion;
import org.qls.typechecker.issues.errors.InconsistentWidgetWithQuestionType;

import java.util.List;


public class QLSQuestionVisitor implements WidgetQuestionVisitor<Void, SymbolTable> {
    private final IdentifierSet definedQuestions = new IdentifierSet();
    private IssuesStorage issuesStorage;

    public void visitStyleSheet(StyleSheet styleSheet, IssuesStorage issuesStorage, SymbolTable symbolTable) {
        this.issuesStorage = issuesStorage;

        visitPages(styleSheet.getPages(), symbolTable);
        checkForUnusedQuestions(symbolTable);
    }

    private void visitPages(List<Page> pages, SymbolTable symbolTable) {
        pages.forEach(page -> visitPage(page, symbolTable));
    }

    private void checkForUnusedQuestions(SymbolTable symbolTable) {
        for (Identifier identifier : symbolTable.identifiers()) {
            if (!definedQuestions.isDeclared(identifier)) {
                issuesStorage.addError(new UnusedQuestion(identifier));
            }
        }
    }

    public void visitPage(Page page, SymbolTable symbolTable) {
        page.getSections().forEach(section -> visitSection(section, symbolTable));
    }

    public void visitSection(Section section, SymbolTable symbolTable) {
        section.getQuestions().forEach(question -> question.accept(this, symbolTable));
        section.getSections().forEach(subSection -> visitSection(subSection, symbolTable));
    }

    @Override
    public Void visitCustomWidgetQuestion(CustomWidgetQuestion question, SymbolTable symbolTable) {
        checkForDuplicatedQuestions(question);
        checkForUndefinedQLQuestions(question, symbolTable);
        checkForQuestionWidgetTypeInconsistency(question, symbolTable);
        return null;
    }

    @Override
    public Void visitGenericWidgetQuestion(WidgetQuestion question, SymbolTable symbolTable) {
        checkForDuplicatedQuestions(question);
        checkForUndefinedQLQuestions(question, symbolTable);
        return null;
    }

    private void checkForQuestionWidgetTypeInconsistency(CustomWidgetQuestion question, SymbolTable symbolTable) {
        if (symbolTable.isDeclared(question.getId()) && !isWidgetTypeSupportedForQuestion(question, symbolTable)) {
            issuesStorage.addError(new InconsistentWidgetWithQuestionType(question));
        }
    }

    private boolean isWidgetTypeSupportedForQuestion(CustomWidgetQuestion question, SymbolTable symbolTable) {
        Type questionType = symbolTable.lookup(question.getId());
        return questionType.isCompatibleWith(question.getWidget());
    }

    private void checkForDuplicatedQuestions(WidgetQuestion question) {
        if (definedQuestions.isDeclared(question.getId())) {
            issuesStorage.addError(new DuplicateQuestion(question));
        } else {
            definedQuestions.declare(question.getId());
        }
    }

    private void checkForUndefinedQLQuestions(WidgetQuestion question, SymbolTable symbolTable) {
        if (!symbolTable.isDeclared(question.getId())) {
            issuesStorage.addError(new UndefinedQuestion(question));
        }
    }
}
