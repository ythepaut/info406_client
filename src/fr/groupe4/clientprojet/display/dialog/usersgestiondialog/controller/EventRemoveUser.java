package fr.groupe4.clientprojet.display.dialog.usersgestiondialog.controller;

import fr.groupe4.clientprojet.communication.Communication;
import fr.groupe4.clientprojet.display.dialog.errordialog.view.ErrorDialog;
import fr.groupe4.clientprojet.display.dialog.usersgestiondialog.view.UsersGestionDialog;
import fr.groupe4.clientprojet.display.view.draw.DrawDialog;
import fr.groupe4.clientprojet.model.project.Project;
import fr.groupe4.clientprojet.model.resource.human.HumanResource;
import fr.groupe4.clientprojet.model.resource.human.HumanResourceProjectList;
import org.jetbrains.annotations.NotNull;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class EventRemoveUser implements ItemListener {
    /**
     * Source parente
     */
    private DrawDialog source;

    /**
     * Utilisateur courant
     */
    private final HumanResource user;

    /**
     * Utilisateur courant
     */
    private final Project project;

    /**
     * Constructeur
     *  @param source Source
     * @param user    Utilisateur courant
     * @param project Projet courant
     */
    public EventRemoveUser(UsersGestionDialog source, HumanResource user, Project project) {
        this.source = source;
        this.user = user;
        this.project = project;
    }

    /**
     * Case coché (Donc utilisateur supprimé)
     *
     * @param itemEvent Event
     */
    @Override
    public void itemStateChanged(ItemEvent itemEvent) {
        Communication comm = Communication.builder().removeHumanResourceFromProject(project.getId(), user.getResourceId()).startNow().sleepUntilFinished().build();
        comm.getResult();
        new ErrorDialog("Utilisateurs bien supprimés", "SUCCESS", ErrorDialog.COLOR_OK, source);
        source.dispose();

    }
}
