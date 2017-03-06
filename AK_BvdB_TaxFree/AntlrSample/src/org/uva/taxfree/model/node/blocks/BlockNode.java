package org.uva.taxfree.model.node.blocks;

import org.uva.taxfree.model.node.Node;
import org.uva.taxfree.model.node.statement.NamedNode;

import java.util.Set;

public abstract class BlockNode extends Node {
    private final Set<Node> mChildren;

    public BlockNode(Set<Node> children) {
        mChildren = children; ///< preserves the order in which the items were inserted
    }

    public void printData() {
        printValue();
        for (Node child : mChildren) {
            child.printValue();
        }
    }

    public void setVisible(boolean isVisible) {
        for (Node child : mChildren) {
            child.setVisible(isVisible);
        }
    }

    protected abstract boolean isVisible();

    public void retrieveDeclarations(Set<NamedNode> set) {
        addDeclaration(set);
    }

    public void retrieveConditions(Set<Node> set) {
        addCondition(set);
    }

    @Override
    public void addCondition(Set<Node> set) {
        for (Node child : mChildren) {
            child.addCondition(set);
        }
    }

    @Override
    public void addDeclaration(Set<NamedNode> set) {
        for (Node child : mChildren) {
            child.addDeclaration(set);
        }
    }

    public void printDeclarations() {
        for (Node child : mChildren) {
            System.out.println(child.toString());
        }
    }

    public void printValues() {
        for (Node child : mChildren) {
            child.printValue();
        }
    }
}