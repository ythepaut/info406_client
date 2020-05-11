package fr.groupe4.clientprojet.display.mainwindow.panels.leftpanel.controller;

import fr.groupe4.clientprojet.display.dialog.projectcreationdialog.view.ProjectCreationDialog;
import fr.groupe4.clientprojet.display.mainwindow.panels.centerpanel.view.CenterPanel;
import fr.groupe4.clientprojet.display.mainwindow.panels.leftpanel.view.LeftPanel;
import fr.groupe4.clientprojet.display.view.RoundButton;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Listener du panel de gauche
 */
public class EventLeftPanel implements ActionListener {
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
     * @param source      : le panel qui fait les appels
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
        if (NEWPROJECT.equals(e.getActionCommand())) {
            new ProjectCreationDialog(source.getOwner());
        } else {
            centerPanel.setView(e.getActionCommand());

            for (RoundButton button : source.getButtons()) {
                if (button.getActionCommand().equals(centerPanel.getView())) button.setSelected(true);
                else button.setSelected(false);
            }
        }
    }
}
