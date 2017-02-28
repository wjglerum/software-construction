# coding=utf-8
from collections import defaultdict


class IdentifierChecker(object):
    def __init__(self):
        # TODO: Dictionary niet als instance variable
        self.identifier_dict = defaultdict(list)

    def visit(self, pql_ast):
        self.identifier_dict.clear()
        [form.apply(self) for form in pql_ast]
        return self.__compute_result(self.identifier_dict)

    def form(self, node):
        [statement.apply(self) for statement in node.children]

    def conditional_if_else(self, node):
        self.conditional_if(node)
        [statement.apply(self) for statement in node.else_statement_list]

    def conditional_if(self, node):
        [statement.apply(self) for statement in node.statements]

    def field(self, node):
        self.identifier_dict[node.name.name].append(node.data_type)

    def __compute_result(self, dictionary):
        def normalize_dictionary(dictionary_):
            # TODO: Mooier maken evt?
            dict_ = {}
            for key, value in dictionary_.items():
                for v in value:
                    dict_[key] = v
            return dict_

        errors = ['Key: {} contained multiple entries, the following: {}'.format(key, value)
                  for (key, value) in dictionary.items() if len(value) > 1]
        return normalize_dictionary(dictionary), errors
