﻿using Questionnaires.Rule;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Questionnaires.RuleContainer
{
    interface IRuleContainer
    {
        void AddRule(IRule rule);
        void ApplyRules(VariableStore.VariableStore variableStore, Renderer.Renderer renderer);
    }
}