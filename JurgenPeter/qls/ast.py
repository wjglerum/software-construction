class Node:
    def __eq__(self, other):
        return type(self) == type(other) and self.__dict__ == other.__dict__


class Layout(Node):

    def __init__(self, name, body):
        self.name = name
        self.body = body

    def accept(self, visitor, *args):
        return visitor.visit_layout(self, *args)


class StyledLayout(Layout):

    def __init__(self, name, body, stylings):
        super().__init__(name, body)
        self.stylings = stylings

    def accept(self, visitor, *args):
        return visitor.visit_styled_layout(self, *args)


class Page(Node):

    def __init__(self, name, body):
        self.name = name
        self.body = body

    def accept(self, visitor, *args):
        return visitor.visit_page(self, *args)


class StyledPage(Page):

    def __init__(self, name, body, stylings):
        super().__init__(name, body)
        self.stylings = stylings

    def accept(self, visitor, *args):
        return visitor.visit_styled_page(self, *args)


class Section(Node):

    def __init__(self, name, body):
        self.name = name
        self.body = body

    def accept(self, visitor, *args):
        return visitor.visit_section(self, *args)


class StyledSection(Section):

    def __init__(self, name, body, stylings):
        super().__init__(name, body)
        self.stylings = stylings

    def accept(self, visitor, *args):
        return visitor.visit_styled_section(self, *args)


class QuestionAnchor(Node):

    def __init__(self, name):
        self.name = name

    def accept(self, visitor, *args):
        return visitor.visit_question_anchor(self, *args)


class StyledQuestionAnchor(QuestionAnchor):

    def __init__(self, name, attributes):
        super().__init__(name)
        self.styling = Styling(attributes)

    def accept(self, visitor, *args):
        return visitor.visit_styled_question_anchor(self, *args)


class Styling(Node):

    def __init__(self, attributes):
        self.attributes = attributes

    def applicable_on(self, datatype):
        return True

    def apply_on(self, widget):
        if self.applicable_on(widget.get_datatype()):
            for attribute in self.attributes:
                attribute.apply_on(widget)

    def accept(self, visitor, *args):
        return visitor.visit_styling(self, *args)


class DefaultStyling(Styling):

    def __init__(self, datatype, attributes):
        super().__init__(attributes)
        self.datatype = datatype

    def applicable_on(self, datatype):
        return self.datatype == datatype


class Attribute(Node):
    pass


class ColorAttribute(Attribute):

    def __init__(self, color):
        self.color = color

    def apply_on(self, widget):
        widget.set_color(self.color)

    def accept(self, visitor, *args):
        return visitor.visit_color_attribute(self, *args)


class FontSizeAttribute(Attribute):

    def __init__(self, size):
        self.size = size

    def apply_on(self, widget):
        widget.set_font_size(self.size)

    def accept(self, visitor, *args):
        return visitor.visit_font_size_attribute(self, *args)


class FontWeightAttribute(Attribute):

    def __init__(self, weight):
        self.weight = weight

    def apply_on(self, widget):
        widget.set_font_weight(self.weight)

    def accept(self, visitor, *args):
        return visitor.visit_font_weight_attribute(self, *args)


class FontFamilyAttribute(Attribute):

    def __init__(self, family):
        self.family = family

    def apply_on(self, widget):
        widget.set_font_family(self.family)

    def accept(self, visitor, *args):
        return visitor.visit_font_family_attribute(self, *args)


class WidthAttribute(Attribute):

    def __init__(self, width):
        self.width = width

    def apply_on(self, widget):
        widget.set_width(self.width)

    def accept(self, visitor, *args):
        return visitor.visit_width_attribute(self, *args)


class WidgetTypeAttribute(Attribute):

    def __init__(self, widget, *widget_arguments):
        self.widget = widget
        self.widget_arguments = list(widget_arguments)

    def apply_on(self, widget):
        pass

    def accept(self, visitor, *args):
        return visitor.visit_widget_type_attribute(self, *args)
