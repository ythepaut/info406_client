package fr.groupe4.clientprojet.display.dialog.usersgestiondialog.controller;

import fr.groupe4.clientprojet.communication.Communication;
import fr.groupe4.clientprojet.display.dialog.errordialog.view.ErrorDialog;
import fr.groupe4.clientprojet.display.view.draw.DrawDialog;
import fr.groupe4.clientprojet.model.project.Project;
import fr.groupe4.clientprojet.model.resource.human.HumanResource;
import fr.groupe4.clientprojet.model.resource.human.HumanResourceList;
import org.jetbrains.annotations.NotNull;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class EventGestionUsersConfirm implements ActionListener {
    /**
     * Parent
     */
    @NotNull
    private final DrawDialog parent;

    /**
     * Utilisateurs choisis
     */
    private final boolean[] chosenUser;

    /**
     * Projet courant
     */
    @NotNull
    private final Project project;

    /**
     * Liste des utilisateurs
     */
    private final HumanResourceList users;

    /**
     * Constructeur
     *
     * @param parent Parent
     * @param project Projet courant
     * @param users Utilisateurs
     * @param chosenUser Utilisateurs sélectionnés
     */
    public EventGestionUsersConfirm(@NotNull DrawDialog parent,
                                    @NotNull Project project,
                                    @NotNull HumanResourceList users,
                                    boolean[] chosenUser) {
        this.parent = parent;
        this.project = project;
        this.chosenUser = chosenUser;
        this.users = users;
    }

    /**
     * Clic bouton
     *
     * @param actionEvent Event
     */
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        ArrayList<Communication> comms = new ArrayList<>();

        for (int i=0; i<users.size(); i++) {
            if (chosenUser[i]) {
                HumanResource h = users.get(i);

                Communication c = Communication.builder()
                        .startNow()
                        .addHumanResourceToProject(project.getId(), h.getResourceId(), null, null)
                        .build();

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

        new ErrorDialog("Utilisateurs bien ajoutés", "SUCCESS", ErrorDialog.COLOR_OK);
        parent.dispose();
    }
}
