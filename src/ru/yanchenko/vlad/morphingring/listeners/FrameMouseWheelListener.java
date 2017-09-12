/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.yanchenko.vlad.morphingring.listeners;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import ru.yanchenko.vlad.morphingring.Repository;

/**
 * @author Влад
 */
public class FrameMouseWheelListener implements MouseWheelListener {

    private Repository repository = Repository.getInstance();

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {

        //<editor-fold defaultstate="collapsed" desc="When no ALT / CTRL / SHIFT keys pressed">
        if (!repository.isKeyAlt()
                && !repository.isKeyCtrl()
                && !repository.isKeyShift()) {
            if (e.getWheelRotation() > 0) {
                if (repository.getRoamingRadius() > 20) {
                    repository.setRoamingRadius(
                            repository.getRoamingRadius()
                                    - repository.getRoamingRadius() / 10);
                }
            } else {
                if (repository.getRoamingRadius() < 500) {
                    repository.setRoamingRadius(
                            repository.getRoamingRadius()
                                    + repository.getRoamingRadius() / 10);
                }
            }
        }
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="When CTRL is pressed">
        if (repository.isKeyCtrl()) {
            if (e.getWheelRotation() > 0) {
                if (repository.getRadiusRoamingStep() > 0.00008) {
                    repository.setRadiusRoamingStep(
                            repository.getRadiusRoamingStep()
                                    - repository.getRadiusRoamingStep() / 10);
                }
            } else {
                if (repository.getRadiusRoamingStep() < 80) {
                    repository.setRadiusRoamingStep(
                            repository.getRadiusRoamingStep()
                                    + repository.getRadiusRoamingStep() / 10);
                }
            }
        }
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="When SHIFT is pressed">
        if (repository.isKeyShift()) {
            if (e.getWheelRotation() > 0) {
                if (repository.getExtraStep() > 0.00005) {
                    repository.setExtraStep(
                            repository.getExtraStep()
                                    - repository.getExtraStep() / 10);
                }
            } else {
                if (repository.getExtraStep() < 0.005) {
                    repository.setExtraStep(
                            repository.getExtraStep()
                                    + repository.getExtraStep() / 10);
                }
            }
        }
        //</editor-fold>

    }
}
