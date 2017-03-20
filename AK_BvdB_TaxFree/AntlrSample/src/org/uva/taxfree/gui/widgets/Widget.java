package org.uva.taxfree.gui.widgets;

import org.uva.taxfree.gui.FormListener;
import org.uva.taxfree.model.environment.SymbolTable;
import org.uva.taxfree.qls.QlsStyle;

import javax.swing.*;
import java.util.List;

public abstract class Widget {
    private final JPanel mPanel;
    private final JLabel mLabel;
    private final String mId;

    public Widget(String label, String id) {
        mLabel = new JLabel(label);
        mPanel = createPanel(label);
        mId = id;
    }

    private JPanel createPanel(String label) {
        JPanel widgetPanel = new JPanel();
        widgetPanel.setName(label);
        widgetPanel.add(mLabel);
        widgetPanel.setVisible(true);
        return widgetPanel;
    }

    public void registerToPanel(JPanel widgetPanel) {
        fillPanel(mPanel);
        widgetPanel.add(mPanel);
    }

    protected abstract void fillPanel(JPanel widgetPanel);

    public abstract String resolveValue();

    public abstract void callOnUpdate(FormListener listener);

    public abstract void updateValues(SymbolTable symbolTable);

    public void updateVisibility(List<String> visibleIds) {
        mPanel.setVisible(visibleIds.contains(mId));
    }


    protected void writeToTable(SymbolTable symbolTable) {
        symbolTable.updateValue(mId, resolveValue());
    }

    protected String readFromTable(SymbolTable symbolTable) {
        return symbolTable.resolveValue(mId);
    }

    public void updateStyle(QlsStyle qlsStyle) {
        applyStyle(mPanel, mLabel, qlsStyle);
    }

    protected void applyStyle(JPanel panel, JLabel label, QlsStyle qlsStyle) {
        // TODO: make abstract
    }

}
