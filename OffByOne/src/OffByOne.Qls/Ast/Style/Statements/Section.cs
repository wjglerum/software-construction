﻿namespace OffByOne.Qls.Ast.Style.Statements
{
    using System.Collections.Generic;

    using OffByOne.Qls.Ast.Style.Rules;
    using OffByOne.Qls.Ast.Style.Statements.Base;
    using OffByOne.Qls.Visitors.Contracts;

    using StringLiteral = OffByOne.Qls.Ast.Style.Literals.StringLiteral;

    public class Section : Statement
    {
        public Section(
            StringLiteral name,
            IEnumerable<Section> sections,
            IEnumerable<QuestionRule> questionRules,
            IEnumerable<ValueTypeRule> valueTypeRules)
        {
            this.Name = name;
            this.Sections = sections;
            this.QuestionRules = questionRules;
            this.ValueTypeRules = valueTypeRules;
        }

        public StringLiteral Name { get; private set; }

        public IEnumerable<Section> Sections { get; set; }

        public IEnumerable<QuestionRule> QuestionRules { get; set; }

        public IEnumerable<ValueTypeRule> ValueTypeRules { get; set; }

        public override TResult Accept<TResult, TContext>(
            IStatementVisitor<TResult, TContext> visitor,
            TContext environment)
        {
            return visitor.Visit(this, environment);
        }
    }
}
