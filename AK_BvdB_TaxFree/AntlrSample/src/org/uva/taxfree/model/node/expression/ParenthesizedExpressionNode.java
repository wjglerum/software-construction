package org.uva.taxfree.model.node.expression;

import org.uva.taxfree.model.node.Node;
import org.uva.taxfree.model.node.condition.ConditionNode;

public class ParenthesizedExpressionNode extends ConditionNode {
    private ConditionNode mChild;

    public ParenthesizedExpressionNode() {
        super();
    }

    @Override
    public void addChild(Node child) {
        if (mChild == null) {
            mChild = (ConditionNode) child;
        } else {
            throw new RuntimeException("Multiple children in parentheses");
        }
    }

    @Override
    public String resolveValue() {
        return mChild.resolveValue();
    }
}
