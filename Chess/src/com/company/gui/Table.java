package com.company.gui;

import javax.swing.*;
import java.awt.*;

public class Table {
    private static final Dimension OUTER_FRAME_DIMENTION = new Dimension(600,600);
    private final JFrame gameFrame;
    public Table() {
        this.gameFrame = new JFrame("Chess");
        this.gameFrame.setSize(OUTER_FRAME_DIMENTION);
        this.gameFrame.setVisible(true);
    }
}
