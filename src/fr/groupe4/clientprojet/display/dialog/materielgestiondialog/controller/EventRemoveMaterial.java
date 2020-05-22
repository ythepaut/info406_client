package fr.groupe4.clientprojet.display.dialog.materielgestiondialog.controller;

import fr.groupe4.clientprojet.communication.Communication;
import fr.groupe4.clientprojet.display.dialog.errordialog.view.ErrorDialog;
import fr.groupe4.clientprojet.display.dialog.materielgestiondialog.view.MaterielGestionDialog;
import fr.groupe4.clientprojet.display.dialog.usersgestiondialog.view.UsersGestionDialog;
import fr.groupe4.clientprojet.display.view.draw.DrawDialog;
import fr.groupe4.clientprojet.model.project.Project;
import fr.groupe4.clientprojet.model.resource.human.HumanResource;
import fr.groupe4.clientprojet.model.resource.material.MaterialResource;
import org.jetbrains.annotations.NotNull;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class EventRemoveMaterial implements ItemListener {
    /**
     * Source parente
     */
    private DrawDialog source;

    /**
     * Utilisateur courant
     */
    private final MaterialResource material;

    /**
     * Utilisateur courant
     */
    private final Project project;

    /**
     * Constructeur
     *  @param source Source
     * @param material Matériel courant
     * @param project Projet courant
     */
    public EventRemoveMaterial(MaterielGestionDialog source, MaterialResource material, Project project) {
        this.source = source;
        this.material = material;
        this.project = project;
    }

    /**
     * Case coché (Donc utilisateur supprimé)
     *
     * @param itemEvent Event
     */
    @Override
    public void itemStateChanged(ItemEvent itemEvent) {
        Communication comm = Communication.builder().removeMaterialResourceFromProject(project.getId(), material.getResourceId()).startNow().sleepUntilFinished().build();
        comm.getResult();
        new ErrorDialog("Matériels bien supprimés", "SUCCESS", ErrorDialog.COLOR_OK, source);
        source.dispose();

    }
}
