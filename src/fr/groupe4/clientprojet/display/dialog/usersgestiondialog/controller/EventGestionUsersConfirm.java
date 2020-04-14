package fr.groupe4.clientprojet.display.dialog.usersgestiondialog.controller;

import fr.groupe4.clientprojet.communication.Communication;
import fr.groupe4.clientprojet.display.dialog.errordialog.view.ErrorDialog;
import fr.groupe4.clientprojet.model.resource.human.HumanResource;
import fr.groupe4.clientprojet.model.resource.human.HumanResourceList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class EventGestionUsersConfirm implements ActionListener {
    /**
     * Utilisateurs choisis
     */
    private boolean[] chosenUser;

    /**
     * Id du projet
     */
    private long projectId;

    private JDialog parent;

    /**
     * Liste des utilisateurs
     */
    private HumanResourceList users;

    public EventGestionUsersConfirm(JDialog parent, long projectId, HumanResourceList users, boolean[] chosenUser) {
        this.parent = parent;
        this.projectId = projectId;
        this.chosenUser = chosenUser;
        this.users = users;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        ArrayList<Communication> comms = new ArrayList<>();

        for (int i=0; i<users.size(); i++) {
            if (chosenUser[i]) {
                HumanResource h = users.get(i);
                Communication c = Communication.builder().startNow().addHumanResourceToProject(projectId, h.getResourceId(), null, null).build();
                comms.add(c);
            }
        }

        for (int i=comms.size()-1; i>=0; i--) {
            Communication c = comms.get(i);

            if (!c.isFinished()) {
                c.sleepUntilFinished();
            }

            users.remove(i);
        }
        new ErrorDialog("Utilisateurs bien ajoutés !", "SUCCESS", new Color(0, 127, 0));
        parent.dispose();
    }
}
