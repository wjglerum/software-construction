﻿namespace OffByOne.Ql.Ast.Expressions.Unary.Base
{
    using System.Collections.Generic;

    public abstract class UnaryExpression : Expression
    {
        protected UnaryExpression(Expression expression)
        {
            this.Expression = expression;
        }

        public Expression Expression { get; private set; }

        public override ISet<string> GetDependencies()
        {
            return this.Expression.GetDependencies();
        }
    }
}
