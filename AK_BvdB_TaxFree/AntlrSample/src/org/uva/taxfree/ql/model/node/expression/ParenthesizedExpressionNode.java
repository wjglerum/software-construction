package org.uva.taxfree.ql.model.node.expression;

import org.uva.taxfree.ql.gui.MessageList;
import org.uva.taxfree.ql.model.SourceInfo;
import org.uva.taxfree.ql.model.environment.SymbolTable;
import org.uva.taxfree.ql.model.types.Type;
import org.uva.taxfree.ql.model.values.Value;

import java.util.Set;

public class ParenthesizedExpressionNode extends ExpressionNode {
    private final ExpressionNode mExpression;

    public ParenthesizedExpressionNode(ExpressionNode expression, SourceInfo sourceInfo) {
        super(sourceInfo);
        mExpression = expression;
    }

    @Override
    public Value evaluate() {
        return mExpression.evaluate();
    }

    @Override
    public void fillSymbolTable(SymbolTable symbolTable) {
        mExpression.fillSymbolTable(symbolTable);
    }

    @Override
    public Type getType() {
        return mExpression.getType();
    }

    @Override
    public void collectUsedVariables(Set<String> dependencies) {
        mExpression.collectUsedVariables(dependencies);
    }

    @Override
    public void checkSemantics(SymbolTable symbolTable, MessageList semanticsMessages) {
        mExpression.checkSemantics(symbolTable, semanticsMessages);
    }

    @Override
    public boolean isConstant() {
        return mExpression.isConstant();
    }

    @Override
    public boolean isLiteral() {
        return mExpression.isLiteral();
    }
}
