﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Questionnaires.Value
{
    class StringValue : Value<String>
    {
        public StringValue(String value)
        {
            this.Val = value;
        }

        public override String AsString()
        {
            return this.Val;
        }

        public override IValue EqualTo(IValue value)
        {
            return EqualTo((dynamic)value);
        }

        public override IValue InequalTo(IValue value)
        {
            return InequalTo((dynamic)value);
        }

        public IValue EqualTo(StringValue value)
        {
            return new BoolValue(this.Val == value.GetValue());
        }

        public IValue InequalTo(StringValue value)
        {
            return new BoolValue(this.Val != value.GetValue());
        }
    }
}
