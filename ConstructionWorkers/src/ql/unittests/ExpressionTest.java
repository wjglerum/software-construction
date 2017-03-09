/**
 * ExpressionTest.java.
 *
 * TODO: add more tests?
 */

package ql.unittests;

import ql.astnodes.LineNumber;
import ql.astnodes.expressions.binaries.equality.*;
import ql.astnodes.expressions.binaries.logic.AND;
import ql.astnodes.expressions.binaries.logic.OR;
import ql.astnodes.expressions.binaries.numerical.Addition;
import ql.astnodes.expressions.binaries.numerical.Division;
import ql.astnodes.expressions.binaries.numerical.Multiplication;
import ql.astnodes.expressions.binaries.numerical.Subtraction;
import ql.astnodes.expressions.literals.Money;
import ql.astnodes.expressions.literals.MyBoolean;
import ql.astnodes.expressions.literals.MyInteger;
import ql.astnodes.expressions.literals.MyString;
import ql.astnodes.expressions.unaries.Negation;
import ql.astnodes.expressions.unaries.Negative;
import ql.astnodes.expressions.unaries.Positive;
import ql.astnodes.types.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ql.gui.evaluation.ExpressionEvaluator;
import ql.gui.formenvironment.Context;
import ql.gui.formenvironment.values.Value;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class ExpressionTest extends QLTestSetUp {

    private Context context = new Context();
    private ExpressionEvaluator expressionEvaluator;

    private final MyInteger testInteger = new MyInteger(1, new LineNumber(1));
    private final MyInteger testInteger2 = new MyInteger(2, null);
    private final MyInteger testInteger4 = new MyInteger(4, null);

    private final Money testMoney = new Money (BigDecimal.valueOf(1.00), new LineNumber(1));
    private final Money testMoney2 = new Money (BigDecimal.valueOf(2.00), null);
    private final Money testMoney4 = new Money (BigDecimal.valueOf(4.00), null);

    private final MyBoolean testBoolean = new MyBoolean (true, new LineNumber(1));

    private final MyString testString = new MyString ("a", new LineNumber(1));

    @Before
    public void setUp() throws IOException{
        inputFileName = "CorrectForm.ql";
        super.setUp();
        expressionEvaluator = new ExpressionEvaluator(context);
    }

    @Test
    public void testTypeCheckerIntegerType() {
        Type integerType = typeChecker.visit(testInteger);
        Assert.assertEquals(integerType.equals(new IntegerType()), true);
    }

    @Test
    public void testTypeCheckerMoneyType() {
        Type moneyType = typeChecker.visit(testMoney);
        Assert.assertEquals(moneyType.equals(new MoneyType()), true);
    }

    @Test
    public void testTypeCheckerBooleanType() {
        Type booleanType = typeChecker.visit(testBoolean);
        Assert.assertEquals(booleanType.equals(new BooleanType()), true);
    }

    @Test
    public void testTypeCheckerStringType() {
        Type stringType = typeChecker.visit(testString);
        Assert.assertEquals(stringType.equals(new StringType()), true);
    }

    @Test
    public void testTypeCheckerIntegerAddition() {
        Addition integerAddition = new Addition(testInteger, testInteger, null);
        Type integerAdditionType = typeChecker.visit(integerAddition);
        Assert.assertEquals(integerAdditionType.equals(new IntegerType()), true);
    }

    @Test
    public void testExpressionEvaluatorIntegerAddition() {
        Addition integerAddition = new Addition(testInteger2, testInteger4, null);
        Value integerAdditionValue = integerAddition.accept(expressionEvaluator);
        Assert.assertEquals(integerAdditionValue.getValue(), 6);
    }

    @Test
    public void testTypeCheckerMoneyAddition() {
        Addition moneyAddition = new Addition(testMoney, testMoney, null);
        Type moneyAdditionType = typeChecker.visit(moneyAddition);
        Assert.assertEquals(moneyAdditionType.equals(new MoneyType()), true);
    }

    @Test
    public void testExpressionEvaluatorMoneyAddition() {
        Addition moneyAddition = new Addition(testMoney2, testMoney4,null);
        Value moneyAdditionValue = moneyAddition.accept(expressionEvaluator);
        Assert.assertEquals(moneyAdditionValue.getValue(), BigDecimal.valueOf(6.00).setScale(2, RoundingMode.HALF_UP));
    }

    @Test
    public void testTypeCheckerIntegerSubtraction() {
        Subtraction integerSubtraction = new Subtraction(testInteger, testInteger, null);
        Type integerSubtractionType = typeChecker.visit(integerSubtraction);
        Assert.assertEquals(integerSubtractionType.equals(new IntegerType()), true);
    }

    @Test
    public void testExpressionEvaluatorIntegerSubtraction() {
        Subtraction integerSubtraction = new Subtraction(testInteger4, testInteger2, null);
        Value integerSubtractionValue = integerSubtraction.accept(expressionEvaluator);
        Assert.assertEquals(integerSubtractionValue.getValue(), 2);
    }

    @Test
    public void testTypeCheckerMoneySubtraction() {
        Subtraction moneySubtraction = new Subtraction(testMoney, testMoney, null);
        Type moneySubtractionType = typeChecker.visit(moneySubtraction);
        Assert.assertEquals(moneySubtractionType.equals(new MoneyType()), true);
    }

    @Test
    public void testExpressionEvaluatorMoneySubtraction() {
        Subtraction moneySubtraction = new Subtraction(testMoney4, testMoney2,null);
        Value moneySubtractionValue = moneySubtraction.accept(expressionEvaluator);
        Assert.assertEquals(moneySubtractionValue.getValue(), BigDecimal.valueOf(2.00).setScale(2, RoundingMode.HALF_UP));
    }

    @Test
    public void testTypeCheckerIntegerMultiplication() {
        Multiplication integerMultiplication = new Multiplication(testInteger, testInteger, null);
        Type integerMultiplicationType = typeChecker.visit(integerMultiplication);
        Assert.assertEquals(integerMultiplicationType.equals(new IntegerType()), true);
    }

    @Test
    public void testExpressionEvaluatorIntegerMultiplication() {
        Multiplication integerMultiplication = new Multiplication(testInteger2, testInteger4, null);
        Value integerMultiplicationValue = integerMultiplication.accept(expressionEvaluator);
        Assert.assertEquals(integerMultiplicationValue.getValue(), 8);
    }

    @Test
    public void testTypeCheckerMoneyMultiplication() {
        Multiplication moneyMultiplication = new Multiplication(testMoney, testMoney, null);
        Type moneyMultiplicationType = typeChecker.visit(moneyMultiplication);
        Assert.assertEquals(moneyMultiplicationType.equals(new MoneyType()), true);
    }

    @Test
    public void testExpressionEvaluatorMoneyMultiplication() {
        Multiplication moneyMultiplication = new Multiplication(testMoney2, testMoney4,null);
        Value moneyMultiplicationValue = moneyMultiplication.accept(expressionEvaluator);
        Assert.assertEquals(moneyMultiplicationValue.getValue(), BigDecimal.valueOf(8.00).setScale(2, RoundingMode.HALF_UP));
    }

    @Test
    public void testTypeCheckerIntegerDivision() {
        Division integerDivision = new Division(testInteger, testInteger, null);
        Type integerDivisionType = typeChecker.visit(integerDivision);
        Assert.assertEquals(integerDivisionType.equals(new IntegerType()), true);
    }

    @Test
    public void testExpressionEvaluatorIntegerDivision() {
        Division integerDivision = new Division(testInteger4, testInteger2, null);
        Value integerDivisionValue = integerDivision.accept(expressionEvaluator);
        Assert.assertEquals(integerDivisionValue.getValue(), 2);
    }

    @Test
    public void testTypeCheckerMoneyDivision() {
        Division moneyDivision = new Division(testMoney, testMoney, null);
        Type moneyDivisionType = typeChecker.visit(moneyDivision);
        Assert.assertEquals(moneyDivisionType.equals(new MoneyType()), true);
    }

    @Test
    public void testExpressionEvaluatorMoneyDivision() {
        Division moneyDivision = new Division(testMoney4, testMoney2,null);
        Value moneyDivisionValue = moneyDivision.accept(expressionEvaluator);
        Assert.assertEquals(moneyDivisionValue.getValue(), BigDecimal.valueOf(2.00).setScale(2, RoundingMode.HALF_UP));
    }

    @Test
    public void testTypeCheckerANDExpression() {
        AND andExpression = new AND(testBoolean, testBoolean, null);
        Type andExpressionType = typeChecker.visit(andExpression);
        Assert.assertEquals(andExpressionType.equals(new BooleanType()), true);
    }

    @Test
    public void testTypeCheckerORExpression() {
        OR orExpression = new OR(testBoolean, testBoolean, null);
        Type orExpressionType = typeChecker.visit(orExpression);
        Assert.assertEquals(orExpressionType.equals(new BooleanType()), true);
    }

    @Test
    public void testTypeCheckerNegationExpression() {
        Negation negationExpression = new Negation(testBoolean, null);
        Type negationExpressionType = typeChecker.visit(negationExpression);
        Assert.assertEquals(negationExpressionType.equals(new BooleanType()), true);
    }

    @Test
    public void testTypeCheckerNegativeIntegerExpression() {
        Negative negativeIntegerExpression = new Negative(testInteger, null);
        Type negativeIntegerExpressionType = typeChecker.visit(negativeIntegerExpression);
        Assert.assertEquals(negativeIntegerExpressionType.equals(new IntegerType()), true);
    }

    @Test
    public void testTypeCheckerPositiveIntegerExpression() {
        Positive positiveIntegerExpression = new Positive(testInteger, null);
        Type positiveIntegerExpressionType = typeChecker.visit(positiveIntegerExpression);
        Assert.assertEquals(positiveIntegerExpressionType.equals(new IntegerType()), true);
    }

    @Test
    public void testTypeCheckerIntegerEQExpression() {
        EQ integerEQExpression = new EQ(testInteger, testInteger, null);
        Type integerEQExpressionType = typeChecker.visit(integerEQExpression);
        Assert.assertEquals(integerEQExpressionType.equals(new BooleanType()), true);
    }

    @Test
    public void testTypeCheckerIntegerNEQExpression() {
        NEQ integerNEQExpression = new NEQ(testInteger, testInteger, null);
        Type integerNEQExpressionType = typeChecker.visit(integerNEQExpression);
        Assert.assertEquals(integerNEQExpressionType.equals(new BooleanType()), true);
    }

    @Test
    public void testTypeCheckerIntegerGTExpression() {
        GT integerGTExpression = new GT(testInteger, testInteger, null);
        Type integerGTExpressionType = typeChecker.visit(integerGTExpression);
        Assert.assertEquals(integerGTExpressionType.equals(new BooleanType()), true);
    }

    @Test
    public void testTypeCheckerIntegerLTExpression() {
        LT integerLTExpression = new LT(testInteger, testInteger, null);
        Type integerLTExpressionType = typeChecker.visit(integerLTExpression);
        Assert.assertEquals(integerLTExpressionType.equals(new BooleanType()), true);
    }

    @Test
    public void testTypeCheckerIntegerGTEQExpression() {
        GTEQ integerGTEQExpression = new GTEQ(testInteger, testInteger, null);
        Type integerGTEQExpressionType = typeChecker.visit(integerGTEQExpression);
        Assert.assertEquals(integerGTEQExpressionType.equals(new BooleanType()), true);
    }

    @Test
    public void testTypeCheckerIntegerLTEQExpression() {
        LTEQ integerLTEQExpression = new LTEQ(testInteger, testInteger, null);
        Type integerLTEQExpressionType = typeChecker.visit(integerLTEQExpression);
        Assert.assertEquals(integerLTEQExpressionType.equals(new BooleanType()), true);
    }
}