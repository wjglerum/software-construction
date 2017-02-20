package ql.ast.literals;

import ql.ast.visistor.ASTVisitor;

/**
 * Created by Erik on 7-2-2017.
 */
public class QLIdent extends QLLiteral {
    private final String qlIdent;

    public QLIdent(String qlIdent) {
        this.qlIdent = qlIdent;
    }

    public String getValue() {
        return qlIdent;
    }

    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
