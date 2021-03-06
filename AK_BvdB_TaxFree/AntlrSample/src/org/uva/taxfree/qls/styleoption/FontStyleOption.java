package org.uva.taxfree.qls.styleoption;

import javax.swing.*;
import java.awt.*;

public class FontStyleOption extends StyleOption {
    private final String mFontName;

    public FontStyleOption(String fontName) {
        mFontName = fontName;
    }

    public void applyStyle(JComponent component) {
        Font currentFont = component.getFont();
        component.setFont(new Font(mFontName, currentFont.getStyle(), currentFont.getSize()));
    }
}
