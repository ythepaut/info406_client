package fr.groupe4.clientprojet.display.mainwindow.panels.leftpanel.controller;

import fr.groupe4.clientprojet.display.dialog.projectcreationdialog.view.ProjectCreationDialog;
import fr.groupe4.clientprojet.display.mainwindow.panels.centerpanel.view.CenterPanel;
import fr.groupe4.clientprojet.display.mainwindow.view.MainWindow;
import fr.groupe4.clientprojet.display.view.RoundButton;
import fr.groupe4.clientprojet.display.mainwindow.panels.leftpanel.view.LeftPanel;

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
    public static final String NEWPROJECT = "newproject";

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
        switch(e.getActionCommand()){
            case NEWPROJECT :
                    new ProjectCreationDialog(source.getOwner());
                break ;
            default:
        centerPanel.setView(e.getActionCommand());

        for (RoundButton button : source.getButtons()) {
            if (button.getActionCommand().equals(centerPanel.getView())) button.setSelected(true);
            else button.setSelected(false);
        }
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
