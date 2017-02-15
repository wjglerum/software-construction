from ParserTokens import ParserTokens as Tokens
import pyparsing as pp
import decimal


class QuestionnaireAST(object):
    def __init__(self, src, loc, tokens):
        self.root = Node("root", src, loc)

        for form in tokens:
            self.root.add_child(form)

    def __eq__(self, other):
        return other.root == self.root

    def __str__(self):
        return self.root.__str__(0)


class Node(object):
    def __init__(self, node_type, src=None, loc=None):
        self.node_type = node_type
        self.children = []

        # Add location info if it is available.
        if src is None or loc is None:
            self.col = self.line = None
        else:
            self.col = pp.col(loc, src)
            self.line = pp.lineno(loc, src)

    def add_child(self, node):
        self.children.append(node)

    def assert_message(self, message):
        return "{}[{},{}]: {}".format(
            self.node_type, self.line, self.col, message
        )

    def __eq__(self, other):
        if other.node_type != self.node_type:
            return False

        for (child_self, child_other) in zip(self.children, other.children):
            if child_self != child_other:
                return False
        return True

    def __str__(self, indent=0):
        output = indent * "  " + "{}:\n".format(self.node_type)

        for child in self.children:
            output += child.__str__(indent + 1)
        return output


class FormNode(Node):
    def __init__(self, src, loc, tokens):
        super(FormNode, self).__init__("form", src, loc)
        form_data = tokens[0]

        assert form_data[0] == "@form", self.assert_message(
            "Form is of invalid type: " + form_data[0]
        )
        self.name = form_data[1].val
        self.form_block = form_data[2]

    def __eq__(self, other):
        return self.node_type == other.node_type and \
               self.form_block == other.form_block

    def __str__(self, indent=0):
        output = indent * "  " + "{}:\n".format(self.node_type)
        output += self.form_block.__str__(indent + 1)
        return output


class BlockNode(Node):
    def __init__(self, src, loc, block_data):
        super(BlockNode, self).__init__("block", src, loc)
        block_data = block_data[0]

        for statement in block_data:
            self.add_child(statement)


class QuestionNode(Node):
    def __init__(self, src, loc, token):
        super(QuestionNode, self).__init__("question", src, loc)
        question = token[0]

        self.question = question[0]
        self.name = question[1].val
        self.type = question[2]

        self.computed = len(question) > 3
        self.expression = None
        if self.computed:
            self.expression = question[3]

    def eval_type(self):
        return self.type

    def __eq__(self, other):
        return other.node_type == self.node_type and \
               other.question == self.question and other.name == self.name and \
               other.type == self.type and other.computed == self.computed and \
               other.expression == self.expression

    def __str__(self, indent=0):
        output = indent * "  " + "{}: {} {}".format(
            self.node_type, self.question, self.type
        )
        if self.computed:
            output += " = ({})".format(self.expression.__str__(0))
        output += "\n"
        return output


class ConditionalNode(Node):
    def __init__(self, src, loc, conditional):
        super(ConditionalNode, self).__init__("conditional", src, loc)
        conditional = conditional[0]

        assert conditional[0] == "@if", self.assert_message(
            "invalid keyword '{}'".format(conditional[0])
        )
        self.expression = conditional[1]
        self.if_block = conditional[2]
        self.else_block = None

        # Else block is optional.
        if len(conditional) == 5:
            assert conditional[3] == "@else", self.assert_message(
                "invalid keyword '{}'".format(conditional[3])
            )
            self.else_block = conditional[4]

    def __eq__(self, other):
        return other.expression == self.expression and \
               self.else_block == other.else_block

    def __str__(self, indent=0):
        output = indent * "  " + "if ({}): \n".format(self.expression)
        output += self.if_block.__str__(indent + 1)

        if self.else_block is not None:
            output += indent * "  " + "else: \n"
            output += self.else_block.__str__(indent + 1)
        return output


class BinOpNode(Node):
    def __init__(self, src, loc, binop):
        super(BinOpNode, self).__init__("binOp", src, loc)

        self.left = binop[0]
        self.op = binop[1]
        self.op_function = Tokens.BINOPS[binop[1]]
        self.right = binop[2]

    def __eq__(self, other):
        return other.node_type == self.node_type and \
               other.left == self.left and other.op == self.op and \
               other.right == self.right

    def __str__(self, indent=0):
        return "({} {} {})".format(
            str(self.left), str(self.op), str(self.right)
        )


class MonOpNode(Node):
    def __init__(self, src, loc, token):
        super(MonOpNode, self).__init__("monOp", src, loc)
        monop = token[0]

        self.op = monop[0]
        self.op_function = Tokens.MONOPS[monop[0]]
        self.right = monop[1]

    def __eq__(self, other):
        return other.node_type == self.node_type and \
               other.op == self.op and other.right == self.right

    def __str__(self, indent=0):
        return "{}{}".format(self.op, str(self.right))


class IntNode(Node):
    def __init__(self, src, loc, token):
        super(IntNode, self).__init__("int", src, loc)
        self.val = decimal.Decimal(token[0])

    def __eq__(self, other):
        return other.node_type == self.node_type and other.val == self.val

    def __str__(self, indent=0):
        return str(self.val)


class BoolNode(Node):
    def __init__(self, src, loc, token):
        super(BoolNode, self).__init__("boolean", src, loc)
        self.val = True if token[0] == "true" else False

    def __eq__(self, other):
        return other.node_type == self.node_type and other.val == self.val

    def __str__(self, indent=0):
        return str(self.val)


class VarNode(Node):
    def __init__(self, src, loc, token):
        super(VarNode, self).__init__("var", src, loc)
        self.val = token[0]

    def __eq__(self, other):
        return other.node_type == self.node_type and other.val == self.val

    def __str__(self, indent=0):
        return str(self.val)


class DecimalNode(Node):
    def __init__(self, src, loc, token):
        super(DecimalNode, self).__init__("dec", src, loc)
        self.val = decimal.Decimal(token[0])

    def __eq__(self, other):
        return other.node_type == self.node_type and other.val == self.val

    def __str__(self, indent=0):
        return str(self.val)
