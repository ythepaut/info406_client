package fr.groupe4.clientprojet.display.view.calendar.view;

import fr.groupe4.clientprojet.model.calendar.CalendarProject;
import fr.groupe4.clientprojet.model.timeslot.TimeSlotList;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

/**
 * Composant générique de calendrier
 *
 * @author Romain
 */
abstract class GenericCalendarComponent {
    /**
     * Labels des titres
     */
    @NotNull
    protected JLabel[] daysTitle;

    /**
     * JPanel parent
     */
    @NotNull
    protected JPanel parent;

    /**
     * Calendrier associé
     */
    @NotNull
    protected CalendarProject calendar;

    /**
     * Panels des jours
     */
    @NotNull
    protected JPanel[] daysPanel;

    /**
     * Contraintes du GridBagLayout
     */
    @NotNull
    protected GridBagConstraints[] constraints;

    /**
     * Liste de tous les créneaux
     */
    @NotNull
    protected TimeSlotList allTimeSlots;

    /**
     * Constructeur
     *
     * @param parent   Fenêtre parente
     * @param calendar Calendrier
     */
    protected GenericCalendarComponent(@NotNull JPanel parent, @NotNull CalendarProject calendar) {
        this.parent = parent;
        this.calendar = calendar;
    }

    /**
     * Initialisation
     */
    protected abstract void init();

    /**
     * Affichage
     *
     * @param g Graphics, où afficher
     */
    protected abstract void paintComponent(Graphics g);
}
