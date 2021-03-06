# coding=utf-8
from collections import defaultdict

from pql.messages.error import Error
from pql.traversal.FormVisitor import FormVisitor


class IdentifierChecker(FormVisitor):
    def __init__(self, ast):
        self.__symbol_table = defaultdict(list)
        self.ast = ast

    def visit(self):
        def build_error_list(identifiers):
            errors = list()
            for key, value in identifiers.items():
                if len(value) > 1:
                    errors.append(Error("Key: {} contained multiple entries, at the following locations: {}"
                                  .format(key, [v.location for v in value]), value[0].location))
            return errors

        self.__symbol_table.clear()
        self.ast.apply(self)
        return build_error_list(self.__symbol_table)

    def form(self, node, args=None):
        for statement in node.statements:
            statement.apply(self)

    def conditional_if_else(self, node, args=None):
        self.conditional_if(node)
        for statement in node.else_statement_list:
            statement.apply(self)

    def conditional_if(self, node, args=None):
        for statement in node.statements:
            statement.apply(self)

    def field(self, node, args=None):
        self.__symbol_table[node.name.name].append(node.name)

    def assignment(self, node, args=None):
        self.field(node)
