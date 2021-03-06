# TODO: Again, remove fake runtime stack with self.indent += 1 etc.
class PrintAst(object):
    def __init__(self, ast):
        """
        :type ast: AST.QuestionnaireAST
        """
        self.ast = ast
        self.indent = 0
        self.output = ""

    def start_traversal(self):
        self.indent = 0
        self.output = ""
        self.form_block(self.ast.root)
        print(self.output)

    def form_block(self, form_node):
        self.output += "form {}: \n".format(form_node.name)
        self.indent += 1
        form_node.form_block.accept(self)
        self.indent -= 1

    def if_node(self, if_node):
        condition = if_node.expression.accept(self)
        self.output += self.indent * "    " + "if ({}):\n".format(condition)
        self.indent += 1
        if_node.if_block.accept(self)
        self.indent -= 1

    def if_else_node(self, if_else_node):
        condition = if_else_node.expression.accept(self)
        self.output += self.indent * "    " + "if ({}):\n".format(condition)
        self.indent += 1
        if_else_node.if_block.accept(self)
        self.indent -= 1
        self.output += self.indent * "    " + "else:\n"
        self.indent += 1
        if_else_node.else_block.accept(self)
        self.indent -= 1

    def question_node(self, question_node):
        self.output += self.indent * "    " + "question: {} {}: {}\n".format(
            question_node.question,
            question_node.name,
            question_node.type.accept(self)
        )

    def comp_question_node(self, comp_question_node):
        self.output += self.indent * "    " + "question: {} {}: {}".format(
            comp_question_node.question,
            comp_question_node.name,
            comp_question_node.type.accept(self)
        )
        expr = comp_question_node.expression.accept(self)
        self.output += " = ({})\n".format(expr)

    def neg_node(self, neg_node):
        output = neg_node.expression.accept(self)
        return "!{}".format(output)

    def min_node(self, min_node):
        output = min_node.expression.accept(self)
        return "-{}".format(output)

    def plus_node(self, plus_node):
        output = plus_node.expression.accept(self)
        return "+{}".format(output)

    def mul_node(self, mul_node):
        left, right = mul_node.left.accept(self), mul_node.right.accept(self)
        return "({} * {})".format(left, right)

    def div_node(self, div_node):
        left, right = div_node.left.accept(self), div_node.right.accept(self)
        return "({} / {})".format(left, right)

    def add_node(self, add_node):
        left, right = add_node.left.accept(self), add_node.right.accept(self)
        return "({} + {})".format(left, right)

    def sub_node(self, sub_node):
        left, right = sub_node.left.accept(self), sub_node.right.accept(self)
        return "({} - {})".format(left, right)

    def lt_node(self, lt_node):
        left, right = lt_node.left.accept(self), lt_node.right.accept(self)
        return "({} < {})".format(left, right)

    def lte_node(self, lte_node):
        left, right = lte_node.left.accept(self), lte_node.right.accept(self)
        return "({} <= {})".format(left, right)

    def gt_node(self, gt_node):
        left, right = gt_node.left.accept(self), gt_node.right.accept(self)
        return "({} > {})".format(left, right)

    def gte_node(self, gte_node):
        left, right = gte_node.left.accept(self), gte_node.right.accept(self)
        return "({} >= {})".format(left, right)

    def eq_node(self, eq_node):
        left, right = eq_node.left.accept(self), eq_node.right.accept(self)
        return "({} == {})".format(left, right)

    def neq_node(self, neq_node):
        left, right = neq_node.left.accept(self), neq_node.right.accept(self)
        return "({} != {})".format(left, right)

    def and_node(self, and_node):
        left, right = and_node.left.accept(self), and_node.right.accept(self)
        return "({} && {})".format(left, right)

    def or_node(self, or_node):
        left, right = or_node.left.accept(self), or_node.right.accept(self)
        return "({} || {})".format(left, right)

    def bool_type_node(self, _):
        return "boolean"

    def int_type_node(self, _):
        return "integer"

    def money_type_node(self, _):
        return "money"

    def decimal_type_node(self, _):
        return "decimal"

    def string_type_node(self, _):
        return "string"

    def date_type_node(self, _):
        return "date"

    def var_node(self, var_node):
        return str(var_node.val)

    def int_node(self, int_node):
        return str(int_node.val)

    def decimal_node(self, decimal_node):
        return str(decimal_node.val)

    def string_node(self, string_node):
        return "'{}'".format(string_node.val)

    def date_node(self, date_node):
        return date_node.val.strftime("%d-%m-%Y")
