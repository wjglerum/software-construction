package org.lemonade.nodes;

import org.lemonade.nodes.expressions.Expression;
import org.lemonade.visitors.interfaces.BaseVisitor;

import java.util.List;

public class Conditional extends Body {

    private Expression condition;
    private List<Body> bodies;

    //How are we going to validate expressions referring to questions declared above?
    public Conditional(Expression expr, List<Body> bodies) {
        super();
        this.condition = expr;
        this.bodies = bodies;
    }

    public List<Body> getBodies() {
        return bodies;
    }

    public Expression getCondition() {
        return condition;
    }

    public <T> T accept(BaseVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public boolean isQuestion() {
        return false;
    }

    @Override
    public boolean isConditional() {
        return true;
    }
    //Validate the org.lemonade.nodes.expressions and test whether it can be reduced to a bool.
}
