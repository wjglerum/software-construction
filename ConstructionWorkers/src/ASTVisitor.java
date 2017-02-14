/**
 * ASTVisitor.java.
 */

import ASTnodes.CodeLocation;
import ASTnodes.Form;
import ASTnodes.Node;
import ASTnodes.expressions.Expression;
import ASTnodes.expressions.binaries.equality.*;
import ASTnodes.expressions.binaries.logic.AND;
import ASTnodes.expressions.binaries.logic.Logic;
import ASTnodes.expressions.binaries.logic.OR;
import ASTnodes.expressions.binaries.numerical.*;
import ASTnodes.expressions.literals.*;
import ASTnodes.expressions.literals.MyInteger;
import ASTnodes.expressions.unaries.*;
import ASTnodes.statements.ComputedQuestion;
import ASTnodes.statements.IfStatement;
import ASTnodes.statements.SimpleQuestion;
import ASTnodes.statements.Statement;
import ASTnodes.types.*;
import antlr.QLBaseVisitor;
import antlr.QLParser;
import antlr.QLVisitor;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class ASTVisitor extends QLBaseVisitor<Node> implements QLVisitor<Node> {

    private static final String GRAMMAR_ERROR = "Why u no work! \\(-o-)/";

    private Form abstractSyntaxTree;

    public ASTVisitor(ParseTree parseTree) {
        abstractSyntaxTree = (Form) parseTree.accept(this);
    }

    public Form getAbstractSyntaxTree() {
        return abstractSyntaxTree;
    }

    public String getGrammerErrorMessage(String expression) {
        return MessageFormat.format(ASTVisitor.GRAMMAR_ERROR, expression);
    }

    @Override
    public Form visitForm(QLParser.FormContext ctx) {
        Identifier identifier = new Identifier(ctx.IDENTIFIER().getText(), getCodeLocation(ctx));
        List<Statement> statements = new ArrayList<>();

        for (QLParser.StatementContext currentStatement : ctx.statement()) {
            statements.add((Statement) currentStatement.accept(this));
        }

        return new Form(identifier, statements, getCodeLocation(ctx));
    }

    @Override
    public SimpleQuestion visitSimpleQuestion(QLParser.SimpleQuestionContext ctx) {
        Type type = (Type) ctx.type().accept(this);
        Identifier identifier = new Identifier(ctx.IDENTIFIER().getText(), getCodeLocation(ctx));
        String label = removeStringQuotes(ctx.STRING().getText());

        return new SimpleQuestion(identifier, label, type, getCodeLocation(ctx));
    }

    @Override
    public ComputedQuestion visitComputedQuestion(QLParser.ComputedQuestionContext ctx) {
        Type type = (Type) ctx.type().accept(this);
        Identifier identifier = new Identifier(ctx.IDENTIFIER().getText(), getCodeLocation(ctx));
        String label = removeStringQuotes(ctx.STRING().getText());
        Expression expression = (Expression) ctx.expression().accept(this);

        return new ComputedQuestion(identifier, label, type, expression, getCodeLocation(ctx));
    }

    @Override
    public IfStatement visitIfStatement(QLParser.IfStatementContext ctx) {
        Expression expression = (Expression) ctx.expression().accept(this);

        List<Statement> statements = new ArrayList<>();

        for (QLParser.StatementContext section : ctx.statement()) {
            Statement stat = (Statement) section.accept(this);
            statements.add(stat);
        }

        return new IfStatement(expression, statements, getCodeLocation(ctx));
    }

    @Override
    public IntegerType visitIntType(QLParser.IntTypeContext ctx) {
        return new IntegerType(getCodeLocation(ctx));
    }

    @Override
    public StringType visitStringType(QLParser.StringTypeContext ctx) {
        return new StringType(getCodeLocation(ctx));
    }

    @Override
    public BooleanType visitBoolType(QLParser.BoolTypeContext ctx) {
        return new BooleanType(getCodeLocation(ctx));
    }

    @Override
    public MoneyType visitMoneyType(QLParser.MoneyTypeContext ctx) {
        return new MoneyType(getCodeLocation(ctx));
    }

    @Override
    public Equality visitComparisonExpression(QLParser.ComparisonExpressionContext ctx) {
        Expression left = (Expression) ctx.expression().get(0).accept(this);
        Expression right = (Expression) ctx.expression().get(1).accept(this);

        switch (ctx.op.getText()) {
            case "==":
                return new EQ(left, right, getCodeLocation(ctx));
            case "!=":
                return new NEQ(left, right, getCodeLocation(ctx));
            case ">":
                return new GT(left, right, getCodeLocation(ctx));
            case "<":
                return new LT(left, right, getCodeLocation(ctx));
            case ">=":
                return new GTEQ(left, right, getCodeLocation(ctx));
            case "<=":
                return new LTEQ(left, right, getCodeLocation(ctx));
            default:
                throw new AssertionError(getGrammerErrorMessage("EqualityExpressions"));
        }
    }

    @Override
    public Numerical visitMultDivExpression(QLParser.MultDivExpressionContext ctx) {
        Expression left = (Expression) ctx.expression(0).accept(this);
        Expression right = (Expression) ctx.expression(1).accept(this);

        switch (ctx.op.getText()) {
            case "*":
                return new Multiplication(left, right, getCodeLocation(ctx));
            case "/":
                return new Division(left, right, getCodeLocation(ctx));
            default:
                throw new AssertionError(getGrammerErrorMessage("MultDivExpressions"));
        }
    }

    @Override
    public Numerical visitAddSubExpression(QLParser.AddSubExpressionContext ctx) {
        Expression left = (Expression) ctx.expression(0).accept(this);
        Expression right = (Expression) ctx.expression(1).accept(this);

        switch (ctx.op.getText()) {
            case "+":
                return new Addition(left, right, getCodeLocation(ctx));
            case "-":
                return new Subtraction(left, right, getCodeLocation(ctx));
            default:
                throw new AssertionError(getGrammerErrorMessage("AddSubExpressions"));
        }
    }

    @Override
    public Parentheses visitParenthesesExpression(QLParser.ParenthesesExpressionContext ctx) {
        Expression expression = (Expression) ctx.expression().accept(this);

        return new Parentheses(expression, getCodeLocation(ctx));
    }

    @Override
    public Unary visitUnaryExpression(QLParser.UnaryExpressionContext ctx) {
        Expression expression = (Expression) ctx.expression().accept(this);

        switch (ctx.op.getText()) {
            case "!":
                return new Negation(expression, getCodeLocation(ctx));
            case "+":
                return new Positive(expression, getCodeLocation(ctx));
            case "-":
                return new Negative(expression, getCodeLocation(ctx));
            default:
                throw new AssertionError(getGrammerErrorMessage("UnaryExpressions"));
        }

    }

    @Override
    public Logic visitAndExpression(QLParser.AndExpressionContext ctx) {
        Expression left = (Expression) ctx.expression(0).accept(this);
        Expression right = (Expression) ctx.expression(1).accept(this);

        return new AND(left, right, getCodeLocation(ctx));
    }

    @Override
    public Logic visitOrExpression(QLParser.OrExpressionContext ctx) {
        Expression left = (Expression) ctx.expression(0).accept(this);
        Expression right = (Expression) ctx.expression(1).accept(this);

        return new OR(left, right, getCodeLocation(ctx));
    }

    @Override
    public MyInteger visitIntExpression(QLParser.IntExpressionContext ctx) {
        return new MyInteger(java.lang.Integer.parseInt(ctx.getText()), getCodeLocation(ctx));
    }

    @Override
    public Money visitMoneyExpression(QLParser.MoneyExpressionContext ctx) {
        return new Money(BigDecimal.valueOf(Double.parseDouble(ctx.getText())), getCodeLocation(ctx));
    }

    @Override
    public Identifier visitIdentifierExpression(QLParser.IdentifierExpressionContext ctx) {
        return new Identifier(ctx.IDENTIFIER().getText(), getCodeLocation(ctx));
    }

    @Override
    public MyBoolean visitBoolExpression(QLParser.BoolExpressionContext ctx) {
        return new MyBoolean(Boolean.parseBoolean(ctx.getText()), getCodeLocation(ctx));
    }

    @Override
    public MyString visitStringExpression(QLParser.StringExpressionContext ctx) {
        return new MyString(removeStringQuotes(ctx.getText()), getCodeLocation(ctx));
    }

    private CodeLocation getCodeLocation(ParserRuleContext ctx) {
        return new CodeLocation(ctx.getStart().getLine());
    }

    private String removeStringQuotes(String targetString) {
        return targetString.substring(1, targetString.length() - 1);
    }
}
