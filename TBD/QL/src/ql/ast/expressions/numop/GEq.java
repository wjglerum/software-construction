package ql.ast.expressions.numop;

import ql.ast.ASTNode;
import ql.ast.Expr;
import ql.ast.expressions.NumOp;
import ql.ast.visistor.ASTVisitor;

/**
 * Created by Erik on 7-2-2017.
 */
public class GEq implements NumOp {
    private Expr left, right;

    public GEq(Expr left, Expr right){
        this.left = left;
        this.right = right;
    }

    public Expr getLeft() {
        return left;
    }

    public Expr getRight() {
        return right;
    }

    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}