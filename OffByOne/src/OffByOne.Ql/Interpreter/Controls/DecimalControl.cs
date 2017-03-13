﻿namespace OffByOne.Ql.Interpreter.Controls
{
    using System;
    using System.Text.RegularExpressions;
    using System.Windows;
    using System.Windows.Controls;
    using System.Windows.Media;

    using OffByOne.Ql.Ast.Statements;
    using OffByOne.Ql.Interpreter.Controls.Base;
    using OffByOne.Ql.Values;

    public class DecimalControl : QuestionControl
    {
        private TextBox input;
        private Label label;

        public DecimalControl(QuestionStatement statement, GuiEnvironment guiEnvironment)
            : base(statement, guiEnvironment)
        {
            this.CreateControl();
            this.Value = new DecimalValue(0.0);
        }

        public override void OnNext(GuiChange value)
        {
            base.OnNext(value);
            this.input.Text = ((DecimalValue)this.Value).ToString();
        }

        public override void OnCompleted()
        {
            throw new NotImplementedException();
        }

        public override void OnError(Exception error)
        {
            throw error;
        }

        private void CreateControl()
        {
            this.label = new Label { Content = this.Statement.Label };
            this.input = new TextBox { MinWidth = 200 };

            this.input.LostFocus += this.Validate;
            this.Controls.Add(this.label);
            this.Controls.Add(this.input);
        }

        private void Validate(object target, RoutedEventArgs eventArgs)
        {
            var text = this.input.Text;

            // TODO: Extract to global constant.
            var filter = new Regex("^-?\\d+((\\.|,)\\d+)?$");
            var isValid = filter.IsMatch(text);
            if (!isValid)
            {
                this.input.BorderBrush = new SolidColorBrush(Colors.DarkRed);
            }
            else
            {
                this.input.BorderBrush = new SolidColorBrush(Colors.Black);
                this.Value = new DecimalValue(text);
            }
        }
    }
}