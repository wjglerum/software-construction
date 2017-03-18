﻿using System.Collections.Generic;

namespace Questionnaires.QL.AST
{
    public interface INode
    {
        bool CheckSemantics(SemanticAnalysis.QLContext context, List<Compilation.Message> messages);
    }
}