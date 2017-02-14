package ql.ast.literals;

import ql.ast.ASTNode;
import ql.ast.QLLiteral;
import ql.ast.visistor.ASTVisitor;

/**
 * Created by Erik on 7-2-2017.
 */
public class QLIdent implements ASTNode, QLLiteral {
    private String qlIdent;

    public QLIdent(String qlIdent){
        this.qlIdent = qlIdent;
    }

    public String getQlIdent() {
        return qlIdent;
    }

    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}