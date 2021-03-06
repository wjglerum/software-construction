package org.ql.ast.identifier;

import java.util.HashSet;
import java.util.Set;

public class IdentifierSet {
    private final Set<Identifier> identifierSet = new HashSet<>();

    public boolean declare(Identifier id) {
        return identifierSet.add(id);
    }

    public boolean isDeclared(Identifier id) {
        return identifierSet.contains(id);
    }

    public int size() {
        return identifierSet.size();
    }
}
