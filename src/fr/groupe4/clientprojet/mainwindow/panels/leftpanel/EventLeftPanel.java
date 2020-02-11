package fr.groupe4.clientprojet.mainwindow.panels.leftpanel;

import fr.groupe4.clientprojet.mainwindow.panels.centerpanel.CenterPanel;
import fr.groupe4.clientprojet.utils.RoundButton;

import java.awt.event.*;

/**
 * Listener du panel de gauche
 */
public class EventLeftPanel implements ActionListener, MouseWheelListener {
    /**
     * Le panel du centre
     */
    private CenterPanel centerPanel;
    /**
     * Le panel qui fait les appels
     */
    private LeftPanel source;

    /**
     * Le constructeur
     *
     * @param source : le panel qui fait les appels
     * @param centerPanel : le panel du centre
     */
    public EventLeftPanel(LeftPanel source, CenterPanel centerPanel) {
        this.source = source;
        this.centerPanel = centerPanel;
    }

    /**
     * Quand un bouton est clické
     *
     * @param e : l'event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        centerPanel.setView(e.getActionCommand());

        for (RoundButton button : source.getButtons()) {
            if (button.getActionCommand().equals(centerPanel.getView())) button.setSelected(true);
            else button.setSelected(false);
        }
    }

    /**
     * Quand la molette de la souris tourne
     *
     * @param e : l'event
     */
    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        source.setDebutListe(source.getDebutListe() + e.getWheelRotation());
        source.redraw();
    }
}
