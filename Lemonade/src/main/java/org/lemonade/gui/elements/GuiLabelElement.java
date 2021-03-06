package org.lemonade.gui.elements;

import org.lemonade.gui.values.GuiStringValue;

import javafx.scene.control.Label;

public class GuiLabelElement implements GuiElement {

    private GuiStringValue value;
    private Label label;

    public GuiLabelElement(String labelText) {
        this.value = new GuiStringValue(labelText);
        this.label = new Label(labelText.replace("\"", ""));
    }

    @Override
    public GuiStringValue getValue() {
        return value;
    }

    @Override
    public Label getWidget() {
        return label;
    }

    // Intentionally left empty as labels are immutable
    @Override
    public void update() {
    }
}
