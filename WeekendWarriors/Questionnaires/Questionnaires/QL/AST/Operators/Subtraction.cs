﻿using Questionnaires.SemanticAnalysis;
using Questionnaires.Types;

namespace Questionnaires.QL.AST.Operators
{
    public class Subtraction : Binary
    {
        public Subtraction(IExpression lhs, IExpression rhs) : base(lhs, rhs)
        {
        }            

        public override IType GetResultType(QLContext context)
        {
            return Lhs.GetResultType(context).Subtract(Rhs.GetResultType(context));
    }
    }
}