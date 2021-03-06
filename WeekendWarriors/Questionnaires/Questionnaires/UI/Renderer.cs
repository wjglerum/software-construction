﻿using Questionnaires.RunTime.DocumentModel;
using System.Windows;
using System.Windows.Controls;

namespace Questionnaires.UI
{
    public class Renderer
    {
        private Window QuestionnaireWindow = new Window();
        private StackPanel QuestionnaireStack = new StackPanel();

        public Renderer()
        {
            QuestionnaireStack.Orientation = Orientation.Vertical;
            QuestionnaireWindow.Content = QuestionnaireStack;
            QuestionnaireWindow.Title = "Questionnaire";
        }

        public void RenderModel(DocumentModel documentModel)
        {
            documentModel.Draw(QuestionnaireStack);
            QuestionnaireWindow.Show();
        }
    }
}
