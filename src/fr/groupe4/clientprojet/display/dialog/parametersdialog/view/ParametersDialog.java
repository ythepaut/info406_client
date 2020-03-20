package fr.groupe4.clientprojet.display.dialog.parametersdialog.view;

import fr.groupe4.clientprojet.display.dialog.parametersdialog.controller.EventParametersDialog;
import fr.groupe4.clientprojet.display.view.draw.DrawDialog;
import fr.groupe4.clientprojet.display.view.slide.view.Slide;

import javax.swing.*;
import java.awt.*;

/**
 * Le dialog des paramètres
 */
public class ParametersDialog extends DrawDialog {
    /**
     * Le listener du dialog
     */
    private EventParametersDialog eventParametersDialog;

    /**
     * Le constructeur
     */
    public ParametersDialog() {
        setTitle("Paramètres");
        setSize(1000, 600);
        setModal(true);
        eventParametersDialog = new EventParametersDialog(this);
        addWindowListener(eventParametersDialog);

        drawContent();

        setVisible(true);
    }

    /**
     * Dessine le contenu
     */
    @Override
    protected void drawContent() {
        setLayout(new BorderLayout());
        Slide slide = new Slide();
        slide.addSlide(generalPanel(), "Général");
        slide.addSlide(appearancePanel(), "Apparence");

        add(slide, BorderLayout.CENTER);
    }

    private JPanel generalPanel() {
        JPanel panel = new JPanel();
        panel.add(new JLabel("Général"));
        return panel;
    }

    private JPanel appearancePanel() {
        JPanel panel = new JPanel();
        panel.add(new JLabel("Apparence"));
        return panel;
    }
}
