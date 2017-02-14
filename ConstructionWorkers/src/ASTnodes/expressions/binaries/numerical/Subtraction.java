/**
 * Subtraction.java.
 */

package ASTnodes.expressions.binaries.numerical;

import ASTnodes.CodeLocation;
import ASTnodes.expressions.Expression;
import ASTnodes.visitors.AllVisitors;

public class Subtraction extends Numerical {

    public Subtraction(Expression left, Expression right, CodeLocation location) {
        super(left, right, location);
    }

    @Override
    public <T> T accept(AllVisitors<T> visitor) {
        return visitor.visit(this);
    }
}
