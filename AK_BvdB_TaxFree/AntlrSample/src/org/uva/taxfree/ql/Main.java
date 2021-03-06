package org.uva.taxfree.ql;

import org.uva.taxfree.ql.ast.AstBuilder;
import org.uva.taxfree.ql.gui.*;
import org.uva.taxfree.ql.model.environment.SymbolTable;
import org.uva.taxfree.ql.model.node.blocks.FormNode;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        File inputFile = FileSelector.select();
        if (!inputFile.exists()) {
            if (MessageWindow.retryDialog(new ErrorMessage("input file: No file selected...\nRetry?"))) {
                main(args);
            }
            return;
        }

        AstBuilder builder = new AstBuilder(inputFile);
        MessageList semanticsMessages = new MessageList();
        FormNode ast = builder.generateTree(semanticsMessages);

        checkMessages(semanticsMessages);

        if (semanticsMessages.hasFatalErrors()) {
            System.exit(1);
        }

        SymbolTable symbolTable = new SymbolTable();
        ast.fillSymbolTable(symbolTable);
        ast.checkSemantics(symbolTable, semanticsMessages);

        checkMessages(semanticsMessages);

        if (semanticsMessages.hasFatalErrors()) {
            System.exit(2);
        }
        QuestionForm taxForm = new QuestionForm(ast.toString(), symbolTable);
        ast.fillQuestionForm(taxForm);
        taxForm.show();
    }

    private static void checkMessages(MessageList semanticsMessages) {
        if (semanticsMessages.hasMessages()) {
            MessageWindow.showMessages(semanticsMessages);
        }
    }
}




