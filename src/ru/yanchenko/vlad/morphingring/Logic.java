package ru.yanchenko.vlad.morphingring;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import javax.swing.Timer;

import ru.yanchenko.vlad.morphingring.generics.Dot;

/*
 * This class is in charge of a logic of a program
 */
public class Logic {

    private static Repository repository;
    private double roamRadius;
    private double radiusRoamStep = 0.0001;
    double increment = 0;

    /**
     * Constructor is done private, for it could not be instantiated anywhere
     * besides "getInstance" method.
     */
    private Logic() {
        instantiateDots();
        сomputeColors();
        repository.setTmrRendering(tmrRendering());
        repository.getTmrRendering().start();
    }

    public static synchronized Logic getInstance(Repository Repository_) {
        repository = Repository_;
        if (Repository_.getoLogic() == null) {
            Repository_.setoLogic(new Logic());
        }
        return Repository_.getoLogic();
    }

    /**
     * Timer that runs a rotation and morphing of a ring.
     *
     * @return
     */
    private Timer tmrRendering() {

        class TimerImpl implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent e) {

                // Recording a time when a computations for each frame begin
                repository.setEndTime((new Date()).getTime());

                computeDots();

                // Repainting a screen
                repository.getoDrawing().repaint();

                // Increasing frames count in 1.
                repository.setFps(repository.getFps() + 1);
                if ((repository.getEndTime() - repository.getBeginTime())
                        >= repository.getFpsUpdateTimeOut()) {
                    // Recording a time when a computations for each frame end
                    repository.setBeginTime((new Date()).getTime());
                    // Such a calculation is required due to a variative fps measurement time
                    repository.getLblFPS().setText("FPS: " + repository.getFps()
                            * (1000 / repository.getFpsUpdateTimeOut()));
                    repository.setFps(0);
                }
            }
        }

        return new Timer(0, new TimerImpl());
    }

    private void instantiateDots() {
        for (int i = 0; i < repository.getDotsQuantity(); i++) {
            repository.getDotsInnerRadius()[i] = new Dot();
            repository.getDotsOuterRadius()[i] = new Dot();
        }
    }

    /**
     * Computing dots of a lines that make up (draw) a ring.
     */
    public void computeDots() {
        double step = 2 * Math.PI / repository.getDotsQuantity();
        double delta = 0;
        roamRadius = repository.getRoamingRadius() * Math.sin(radiusRoamStep);
        radiusRoamStep += repository.getRadiusRoamingStep();
        for (int i = 0; i < repository.getDotsQuantity(); i++) {
            repository.setInnerRadius(
                    (int)(200 + roamRadius * Math.cos(delta * 20 + increment))
            );
            repository.setOuterRadius(
                    (int)(350 + roamRadius * Math.sin((delta + 1.18) * 20 + increment))
            );
            repository.getDotsInnerRadius()[i].setX(
                    repository.getPntScreenCenter().x
                            + Math.sin(delta + increment)
                            * repository.getInnerRadius()
            );
            repository.getDotsInnerRadius()[i].setY(
                    repository.getPntScreenCenter().y
                            - Math.cos(delta + increment)
                            * repository.getInnerRadius()
            );
            repository.getDotsOuterRadius()[i].setX(
                    repository.getPntScreenCenter().x
                            + Math.sin(delta + increment)
                            * repository.getOuterRadius()
            );
            repository.getDotsOuterRadius()[i].setY(
                    repository.getPntScreenCenter().y
                            - Math.cos(delta + increment)
                            * repository.getOuterRadius()
            );
            delta += step;
            increment += repository.getExtraStep();
        }
    }

    /**
     * Making a rainbow colors
     */
    public void сomputeColors() {
        float r = 255f;
        float g = 0f;
        float b = 0f;
        int j = 0;  // Current color index in a dotsColors array.
        double step = Repository.MAX_COLOR_VALUE /
                (repository.getDotsQuantity() / 6.);
        // Steps taken to change a color gradually
        int steps = repository.getDotsQuantity() / 6;
        for (int i = 0; i < steps; i++) {
            repository.getDotsColors()[j] = new Color((int) r, (int) g, (int) b);
//            System.out.println(repository.getDotsColors()[i].getRed()
//                    + " " + repository.getDotsColors()[i].getGreen()
//                    + " " + repository.getDotsColors()[i].getBlue());
            g += step;
            j++;
        }
        repository.getDotsColors()[j] = new Color((int) r, (int) g, (int) b);
        for (int i = 0; i < steps; i++) {
            repository.getDotsColors()[j] = new Color((int) r, (int) g, (int) b);
            r -= step;
            j++;
        }
        if (r < 0) r = 0;
        repository.getDotsColors()[j] = new Color((int) r, (int) g, (int) b);
        for (int i = 0; i < steps; i++) {
            repository.getDotsColors()[j] = new Color((int) r, (int) g, (int) b);
            b += step;
            j++;
        }
        repository.getDotsColors()[j] = new Color((int) r, (int) g, (int) b);
        for (int i = 0; i < steps; i++) {
            repository.getDotsColors()[j] = new Color((int) r, (int) g, (int) b);
            g -= step;
            j++;
        }
        if (g < 0) g = 0;
        repository.getDotsColors()[j] = new Color((int) r, (int) g, (int) b);
        for (int i = 0; i < steps; i++) {
            repository.getDotsColors()[j] = new Color((int) r, (int) g, (int) b);
            r += step;
            j++;
        }
        repository.getDotsColors()[j] = new Color((int) r, (int) g, (int) b);
        for (int i = 0; i < steps; i++) {
            repository.getDotsColors()[j] = new Color((int) r, (int) g, (int) b);
            b -= step;
            j++;
        }
        if (b < 0) b = 0;
        repository.getDotsColors()[j] = new Color((int) r, (int) g, (int) b);
        while (j < repository.getDotsQuantity()) {
            System.out.println("Extra colors are added");
            repository.getDotsColors()[j] = new Color((int) r, (int) g, (int) b);
            j++;
        }

    }

}