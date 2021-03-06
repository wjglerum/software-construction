package org.uva.taxfree.ql.model.node.expression;

import org.uva.taxfree.ql.gui.MessageList;
import org.uva.taxfree.ql.model.SourceInfo;
import org.uva.taxfree.ql.model.environment.SymbolTable;
import org.uva.taxfree.ql.model.operators.Operator;
import org.uva.taxfree.ql.model.types.Type;
import org.uva.taxfree.ql.model.values.Value;

import java.util.List;
import java.util.Set;

public class BinaryExpressionNode extends ExpressionNode {
    private final ExpressionNode mLeft;
    private final Operator mOperator;
    private final ExpressionNode mRight;

    public BinaryExpressionNode(ExpressionNode left, Operator operator, ExpressionNode right, SourceInfo sourceInfo) {
        super(sourceInfo);
        mLeft = left;
        mOperator = operator;
        mRight = right;
    }

    @Override
    public Value evaluate() {
        return mOperator.evaluate(mLeft, mRight);
    }

    @Override
    public void fillSymbolTable(SymbolTable symbolTable) {
        mLeft.fillSymbolTable(symbolTable);
        mRight.fillSymbolTable(symbolTable);
    }

    @Override
    public void checkSemantics(SymbolTable symbolTable, MessageList semanticsMessages) {
        mLeft.checkSemantics(symbolTable, semanticsMessages);
        mRight.checkSemantics(symbolTable, semanticsMessages);
        if (!mLeft.isSameType(mRight)) {
            semanticsMessages.addError(sourceString() + "Incompatible types in expression: " + mLeft.getType() + " & " + mRight.getType());
        } else {
            if (!mOperator.supports(mLeft.getType(), mRight.getType())) {
                semanticsMessages.addError(sourceString() + "Unsupported operator called:" + mLeft.getType() + " " + mOperator + " " + mRight.getType());
            } else if (isConstant()) {
                semanticsMessages.addWarning(sourceString() + "Constant expression found, always evaluates to: " + evaluate());
            }
        }
    }

    @Override
    public boolean isConstant() {
        return mLeft.isConstant() && mRight.isConstant();
    }

    @Override
    public boolean isLiteral() {
        return false;
    }

    @Override
    public void collectUsedVariables(Set<String> dependencies) {
        mLeft.collectUsedVariables(dependencies);
        mRight.collectUsedVariables(dependencies);
    }

    @Override
    public void generateVisibleIds(List<String> visibleIds) {
        // Intentionally left blank
    }

    @Override
    public Type getType() {
        return mOperator.getType();
    }
}
