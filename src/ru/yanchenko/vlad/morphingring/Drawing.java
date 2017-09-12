package ru.yanchenko.vlad.morphingring;

import java.awt.*;
import javax.swing.JPanel;

public class Drawing extends JPanel {

    private static Repository repository;

    /**
     * Constructor is made private, for it could not be instantiated anywhere
     * besides "getInstance" method.
     */
    private Drawing() {
    }

    public static synchronized Drawing getInstance(Repository Repository_) {
        repository = Repository_;
        if (Repository_.getoDrawing() == null) {
            Repository_.setoDrawing(new Drawing());
        }
        return Repository_.getoDrawing();
    }

    @Override
    public void paintComponent(Graphics g) {

        /**
         * Hiding mouse cursor is turned on in Repository.initializeData() by
         * uncommenting a respective code.
         */

        //** Erasing a previously drawn data 
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        for (int i = 0; i < repository.getDotsQuantity(); i++) {
            g2.setColor(repository.getDotsColors()[i]);
            g2.drawLine(
                    (int) repository.getDotsInnerRadius()[i].getX(),
                    (int) repository.getDotsInnerRadius()[i].getY(),
                    (int) repository.getDotsOuterRadius()[i].getX(),
                    (int) repository.getDotsOuterRadius()[i].getY()
            );
        }

    }
}
