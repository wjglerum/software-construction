package qls.astnodes.widgets;

import ql.astnodes.LineNumber;
import ql.astnodes.types.StringType;
import ql.astnodes.types.Type;
import ql.gui.formenvironment.values.StringValue;
import ql.gui.formenvironment.values.Value;
import qls.astnodes.styles.Style;
import qls.astnodes.styles.Width;
import qls.astnodes.visitors.StyleSheetVisitor;
import qls.astnodes.widgets.widgettypes.TextBoxType;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.*;

/**
 * Created by LGGX on 04-Mar-17.
 */
public class QLSTextBox extends QLSWidget {

    public final static int DEFAULT_WIDTH = 7;

    private JTextField input;

    public QLSTextBox() {

    }

    public QLSTextBox(String _label, LineNumber lineNumber) {
        super(lineNumber);
        this.input = new JTextField();
        this.componentLabel.setText(_label);
        this.component.add(componentLabel);
        this.component.add(input);

        this.type = new TextBoxType();
    }

    @Override
    public void setLabel(String _label) {
        this.componentLabel.setText(_label);
    }

    @Override
    public void applyStyle(Style style) {
        style.getInheritedStyle(this.getDefaultStyle());

        Font font = new Font(
                style.getFont(this.getDefaultFont().getValue()), 0,
                style.getFontSize(Integer.parseInt(this.getDefaultFontSize().getValue()))
        );
        this.componentLabel.setFont(font);

        Color color = style.getColor(Integer.parseInt(this.getDefaultColor().getValue()));
        this.componentLabel.setForeground(color);
        this.input.setColumns(Integer.parseInt(this.getDefaultWidth().getValue()));
    }

    @Override
    public void addListener(EventListener listener) {

        this.input.getDocument().addDocumentListener(
                new DocumentListener() {
                    public void insertUpdate(DocumentEvent e) {

                    }
                    public void removeUpdate(DocumentEvent e) {}
                    public void changedUpdate(DocumentEvent e) {}
                }
        );
    }

    @Override
    public StringValue getValue() {
        return new StringValue(this.input.getText());
    }

    @Override
    public void setValue(Value nvalue) {
        StringValue value = (StringValue) nvalue;
        this.input.setText(value.getValue());
    }

    @Override
    public void setReadOnly(boolean _isReadonly) {
        this.input.setEnabled(false);
    }

    @Override
    public Width getDefaultWidth() {
        return new Width(DEFAULT_WIDTH, getLineNumber());
    }

    public java.util.List<Type> getSupportedQuestionTypes() {
        java.util.List<Type> supportedTypes = new ArrayList<>(
                Arrays.asList(new StringType())
        );
        return supportedTypes;
    }

    public <T> T accept(StyleSheetVisitor<T> visitor) {
        return visitor.visit(this);
    }
}