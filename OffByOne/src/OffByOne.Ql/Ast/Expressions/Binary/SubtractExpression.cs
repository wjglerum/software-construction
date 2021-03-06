﻿namespace OffByOne.Ql.Ast.Expressions.Binary
{
    using OffByOne.Ql.Ast.Expressions.Binary.Base;
    using OffByOne.Ql.Visitors.Contracts;

    public class SubtractExpression : BinaryExpression
    {
        public SubtractExpression(
            Expression leftExpression,
            Expression rightExpression)
            : base(leftExpression, rightExpression)
        {
        }

        public override TResult Accept<TResult, TContext>(
            IExpressionVisitor<TResult, TContext> visitor,
            TContext environment)
        {
            return visitor.Visit(this, environment);
        }
    }
}
