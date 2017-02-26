﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Controls;
using Questionnaires.Question;
using Questionnaires.Value;

namespace Questionnaires.Renderer.Widgets
{
    abstract class QuestionWidget : StackPanel, IQuestionWidget
    {
        public abstract void SetLabel(string text);
        public abstract void SetOnInputChanged(Renderer.InputChangedCallback inputChanged);
        public abstract void SetQuestionValue(IValue value);
        public abstract void SetVisibility(Visibility visibility);
    }
}
