package ql.gui.expressions.binop;

import ql.ast.Expr;
import ql.gui.expressions.BinOp;
import ql.gui.expressions.RelationalOp;
import ql.visistor.interfaces.ExpressionVisitor;


/**
 * Created by Erik on 7-2-2017.
 */
public class GT extends BinOp implements RelationalOp {
    public GT(Expr left, Expr right, int rowNumber) {
        super(left, right, rowNumber);
    }

    public <T> T accept(ExpressionVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
