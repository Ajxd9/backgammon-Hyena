package com.HyenaBgammon.view;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.RenderingHints;
import javax.swing.ButtonModel;
import javax.swing.JButton;

public class ReplayBarButton extends JButton {
    private static final long serialVersionUID = 1311999467069321850L;

    public ReplayBarButton(String text) {
        build();
    }

    private void build() {
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Paint paint;
        int height = getHeight();
        int width = getWidth();

        ButtonModel model = getModel();

        float alpha = 0.3f;
        if (model.isRollover()) {
            alpha += 0.2f;
        }
        if (model.isPressed()) {
            alpha += 0.2f;
        }
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));

        // Background
        paint = new Color(0x000000);

        g2.setPaint(paint);
        g2.fillRect(0, 0, width, height);

        g2.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {}
}
