package GUI.GUIEvaluation;

import ASTnodes.expressions.binaries.equality.*;
import ASTnodes.expressions.binaries.logic.AND;
import ASTnodes.expressions.binaries.logic.OR;
import ASTnodes.expressions.binaries.numerical.Addition;
import ASTnodes.expressions.binaries.numerical.Division;
import ASTnodes.expressions.binaries.numerical.Multiplication;
import ASTnodes.expressions.binaries.numerical.Subtraction;
import ASTnodes.expressions.literals.*;
import ASTnodes.expressions.unaries.Negation;
import ASTnodes.expressions.unaries.Negative;
import ASTnodes.expressions.unaries.Parenthesis;
import ASTnodes.expressions.unaries.Positive;
import ASTnodes.visitors.ExpressionVisitor;
import semanticChecker.formDataStorage.valueData.ValueData;
import semanticChecker.formDataStorage.valueData.values.*;

/**
 * Created by LGGX on 24-Feb-17.
 */
public class QuestionValueChecker implements ExpressionVisitor<Value>{

    private final ValueData valueData;

    public QuestionValueChecker(ValueData values) {
        this.valueData = values;
    }

    @Override
    public Value visit(Parenthesis expression) {
        Value express = expression.getExpression().accept(this);
        return express;
    }

    @Override
    public Value visit(Negation expression) {
        Value express = expression.getExpression().accept(this);
        return express.negation();
    }

    @Override
    public Value visit(Negative expression) {
        Value express = expression.getExpression().accept(this);
        return express.negative();
    }

    @Override
    public Value visit(Positive expression) {
        Value express = expression.getExpression().accept(this);
        return express.positive();
    }

    @Override
    public Value visit(AND expression) {
        Value left = expression.getLeft().accept(this);
        Value right = expression.getRight().accept(this);

        return left.and(right);
    }

    @Override
    public Value visit(OR expression) {
        Value left = expression.getLeft().accept(this);
        Value right = expression.getRight().accept(this);

        return left.or(right);
    }

    @Override
    public Value visit(Addition expression) {
        Value left = expression.getLeft().accept(this);
        Value right = expression.getRight().accept(this);

        return left.addition(right);
    }

    @Override
    public Value visit(Subtraction expression) {
        Value left = expression.getLeft().accept(this);
        Value right = expression.getRight().accept(this);

        return left.subtraction(right);
    }

    @Override
    public Value visit(Multiplication expression) {
        Value left = expression.getLeft().accept(this);
        Value right = expression.getRight().accept(this);

        return left.multiplication(right);
    }

    @Override
    public Value visit(Division expression) {
        Value left = expression.getLeft().accept(this);
        Value right = expression.getRight().accept(this);

        return left.division(right);
    }

    @Override
    public Value visit(EQ expression) {
        Value left = expression.getLeft().accept(this);
        Value right = expression.getRight().accept(this);

        return left.eq(right);
    }

    @Override
    public Value visit(NEQ expression) {
        Value left = expression.getLeft().accept(this);
        Value right = expression.getRight().accept(this);

        return left.neq(right);
    }

    @Override
    public Value visit(GT expression) {
        Value left = expression.getLeft().accept(this);
        Value right = expression.getRight().accept(this);

        return left.gt(right);
    }

    @Override
    public Value visit(LT expression) {
        Value left = expression.getLeft().accept(this);
        Value right = expression.getRight().accept(this);

        return left.lt(right);
    }

    @Override
    public Value visit(GTEQ expression) {
        Value left = expression.getLeft().accept(this);
        Value right = expression.getRight().accept(this);

        return left.gteq(right);
    }

    @Override
    public Value visit(LTEQ expression) {
        Value left = expression.getLeft().accept(this);
        Value right = expression.getRight().accept(this);

        return left.lteq(right);
    }

    @Override
    public Value visit(Identifier literal) {
        return this.valueData.getValue(literal.getName());
    }

    @Override
    public Value visit(MyInteger literal) {
        return new IntegerValue(literal.getValue());
    }

    @Override
    public Value visit(Money literal) {
        return new MoneyValue(literal.getValue());
    }

    @Override
    public Value visit(MyString literal) {
        return new StringValue(literal.getValue());
    }

    @Override
    public Value visit(MyBoolean literal) {
        return new BooleanValue(literal.getValue());
    }
}