﻿namespace OffByOne.Ql.Checker
{
    using System.Collections.Generic;

    using MoreDotNet.Extensions.Collections;
    using MoreDotNet.Extensions.Common;

    using OffByOne.Ql.Ast.Expressions;
    using OffByOne.Ql.Ast.Expressions.Binary;
    using OffByOne.Ql.Ast.Expressions.Binary.Base;
    using OffByOne.Ql.Ast.Expressions.Unary;
    using OffByOne.Ql.Ast.Expressions.Unary.Base;
    using OffByOne.Ql.Ast.Literals;
    using OffByOne.Ql.Ast.Statements;
    using OffByOne.Ql.Ast.ValueTypes;
    using OffByOne.Ql.Ast.ValueTypes.Base;
    using OffByOne.Ql.Checker.Messages;
    using OffByOne.Ql.Visitors.Contracts;

    public class TypeVisitor
        : IExpressionVisitor<ValueType, VisitorTypeEnvironment>,
        IStatementVisitor<VoidValueType, VisitorTypeEnvironment>
    {
        private static readonly IEnumerable<ValueType> NumericValueTypes = new List<ValueType>()
        {
            new IntegerValueType(),
            new DecimalValueType(),
            new MoneyValueType()
        };

        public TypeVisitor()
            : this(new CheckerReport())
        {
        }

        public TypeVisitor(CheckerReport report)
        {
            this.Report = report;
        }

        public CheckerReport Report { get; }

        public ValueType Visit(AddExpression expression, VisitorTypeEnvironment environment)
        {
            return this.CheckBinaryMatematicalExpression(expression, environment);
        }

        public ValueType Visit(SubtractExpression expression, VisitorTypeEnvironment environment)
        {
            return this.CheckBinaryMatematicalExpression(expression, environment);
        }

        public ValueType Visit(MultiplyExpression expression, VisitorTypeEnvironment environment)
        {
            return this.CheckBinaryMatematicalExpression(expression, environment);
        }

        public ValueType Visit(DivideExpression expression, VisitorTypeEnvironment environment)
        {
            return this.CheckBinaryMatematicalExpression(expression, environment);
        }

        public ValueType Visit(AndExpression expression, VisitorTypeEnvironment environment)
        {
            return this.CheckBinaryBooleanLogicExpression(expression, environment);
        }

        public ValueType Visit(OrExpression expression, VisitorTypeEnvironment environment)
        {
            return this.CheckBinaryBooleanLogicExpression(expression, environment);
        }

        public ValueType Visit(EqualExpression expression, VisitorTypeEnvironment environment)
        {
            return this.CheckBinaryComparisonExpression(expression, environment);
        }

        public ValueType Visit(NotEqualExpression expression, VisitorTypeEnvironment environment)
        {
            return this.CheckBinaryComparisonExpression(expression, environment);
        }

        public ValueType Visit(GreaterThanExpression expression, VisitorTypeEnvironment environment)
        {
            return this.CheckBinaryComparisonExpression(expression, environment);
        }

        public ValueType Visit(GreaterThanOrEqualExpression expression, VisitorTypeEnvironment environment)
        {
            return this.CheckBinaryComparisonExpression(expression, environment);
        }

        public ValueType Visit(LessThanExpression expression, VisitorTypeEnvironment environment)
        {
            return this.CheckBinaryComparisonExpression(expression, environment);
        }

        public ValueType Visit(LessThanOrEqualExpression expression, VisitorTypeEnvironment environment)
        {
            return this.CheckBinaryComparisonExpression(expression, environment);
        }

        public ValueType Visit(NotExpression expression, VisitorTypeEnvironment environment)
        {
            return this.CheckUnaryBooleanLogicExpression(expression, environment);
        }

        public ValueType Visit(NegativeExpression expression, VisitorTypeEnvironment environment)
        {
            return this.CheckUnaryMatematicalExpression(expression, environment);
        }

        public ValueType Visit(PositiveExpression expression, VisitorTypeEnvironment environment)
        {
            return this.CheckUnaryMatematicalExpression(expression, environment);
        }

        public ValueType Visit(VariableExpression expression, VisitorTypeEnvironment environment)
        {
            var quetionType = environment.GetTypeOf(expression.Identifier);
            if (quetionType == null)
            {
                this.Report.Add(new UndeclaredVariableMessage(expression));
                return new VoidValueType();
            }

            return quetionType;
        }

        public ValueType Visit(BracketExpression expression, VisitorTypeEnvironment environment)
        {
            return expression.Expression.Accept(this, environment);
        }

        public VoidValueType Visit(QuestionStatement expression, VisitorTypeEnvironment environment)
        {
            environment.AddSymbol(expression.Identifier, expression.Type);
            return new VoidValueType();
        }

        public VoidValueType Visit(IfStatement expression, VisitorTypeEnvironment environment)
        {
            var result = this.CheckIfStatement(expression, environment);
            expression.Statements.ForEach(x => x.Accept(this, environment));
            expression.ElseStatements.ForEach(x => x.Accept(this, environment));

            return new VoidValueType();
        }

        public VoidValueType Visit(FormStatement expression, VisitorTypeEnvironment environment)
        {
            expression.Statements.ForEach(x => x.Accept(this, environment));

            return new VoidValueType();
        }

        public ValueType Visit(IntegerLiteral literal, VisitorTypeEnvironment environment)
        {
            return new IntegerValueType();
        }

        public ValueType Visit(MoneyLiteral literal, VisitorTypeEnvironment environment)
        {
            return new MoneyValueType();
        }

        public ValueType Visit(DecimalLiteral literal, VisitorTypeEnvironment environment)
        {
            return new DecimalValueType();
        }

        public ValueType Visit(BooleanLiteral literal, VisitorTypeEnvironment environment)
        {
            return new BooleanValueType();
        }

        public ValueType Visit(StringLiteral literal, VisitorTypeEnvironment environment)
        {
            return new StringValueType();
        }

        public ValueType Visit(DateLiteral literal, VisitorTypeEnvironment environment)
        {
            return new DateValueType();
        }

        public ValueType Visit(HexLiteral literal, VisitorTypeEnvironment environment)
        {
            return new StringValueType();
        }

        private ValueType CheckBinaryMatematicalExpression(BinaryExpression expression, VisitorTypeEnvironment context)
        {
            var leftExpressionType = expression.LeftExpression.Accept(this, context);
            var rightExpressionType = expression.RightExpression.Accept(this, context);

            if (leftExpressionType != new IntegerValueType()
                && leftExpressionType != new DecimalValueType()
                && leftExpressionType != new MoneyValueType())
            {
                this.Report.Add(new InvalidTypeMessage(
                    expression.LeftExpression,
                    NumericValueTypes,
                    leftExpressionType));

                return new VoidValueType();
            }

            if (leftExpressionType != new IntegerValueType()
                && leftExpressionType != new DecimalValueType()
                && leftExpressionType != new MoneyValueType())
            {
                this.Report.Add(new InvalidTypeMessage(
                    expression.RightExpression,
                    NumericValueTypes,
                    rightExpressionType));
            }

            if (leftExpressionType == new IntegerValueType()
                && rightExpressionType == new IntegerValueType())
            {
                return new IntegerValueType();
            }

            if (leftExpressionType == new DecimalValueType()
                || rightExpressionType == new DecimalValueType())
            {
                return new DecimalValueType();
            }

            if (leftExpressionType == new MoneyValueType()
                || rightExpressionType == new MoneyValueType())
            {
                return new MoneyValueType();
            }

            return new VoidValueType();
        }

        private ValueType CheckUnaryMatematicalExpression(UnaryExpression expression, VisitorTypeEnvironment context)
        {
            var subExpressionType = expression.Expression.Accept(this, context);
            if (subExpressionType.IsNot<NumericalValueType>())
            {
                this.Report.Add(new InvalidTypeMessage(
                    expression,
                    NumericValueTypes,
                    subExpressionType));
            }

            return subExpressionType;
        }

        private ValueType CheckBinaryComparisonExpression(BinaryExpression expression, VisitorTypeEnvironment context)
        {
            var leftExpressionType = expression.LeftExpression.Accept(this, context);
            var rightExpressionType = expression.RightExpression.Accept(this, context);

            if (leftExpressionType != rightExpressionType)
            {
                this.Report.Add(new InvalidTypeMessage(
                    expression,
                    leftExpressionType,
                    rightExpressionType));
            }

            return new BooleanValueType();
        }

        private ValueType CheckBinaryBooleanLogicExpression(BinaryExpression expression, VisitorTypeEnvironment context)
        {
            var leftExpressionType = expression.LeftExpression.Accept(this, context);
            var rightEpressionType = expression.RightExpression.Accept(this, context);

            if (leftExpressionType != new BooleanValueType())
            {
                this.Report.Add(new InvalidTypeMessage(
                    expression.LeftExpression,
                    new BooleanValueType(),
                    leftExpressionType));

                return leftExpressionType;
            }

            if (rightEpressionType != new BooleanValueType())
            {
                this.Report.Add(new InvalidTypeMessage(
                    expression.RightExpression,
                    new BooleanValueType(),
                    rightEpressionType));

                return rightEpressionType;
            }

            return new BooleanValueType();
        }

        private ValueType CheckUnaryBooleanLogicExpression(UnaryExpression expression, VisitorTypeEnvironment context)
        {
            var subExpressionType = expression.Expression.Accept(this, context);

            if (subExpressionType != new BooleanValueType())
            {
                this.Report.Add(new InvalidTypeMessage(
                    expression,
                    new BooleanValueType(),
                    subExpressionType));

                return subExpressionType;
            }

            return new BooleanValueType();
        }

        private ValueType CheckIfStatement(IfStatement statement, VisitorTypeEnvironment context)
        {
            var conditionType = statement.Condition.Accept(this, context);
            if (conditionType != new BooleanValueType())
            {
                this.Report.Add(new InvalidTypeMessage(statement, new BooleanValueType(), conditionType));
            }

            return new VoidValueType();
        }
    }
}
