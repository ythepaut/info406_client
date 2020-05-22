package fr.groupe4.clientprojet.display.dialog.materielgestiondialog.controller;

import fr.groupe4.clientprojet.communication.Communication;
import fr.groupe4.clientprojet.display.dialog.errordialog.view.ErrorDialog;
import fr.groupe4.clientprojet.display.dialog.materielgestiondialog.view.MaterielGestionDialog;
import fr.groupe4.clientprojet.display.view.draw.DrawDialog;
import fr.groupe4.clientprojet.model.project.Project;
import fr.groupe4.clientprojet.model.resource.human.HumanResource;
import fr.groupe4.clientprojet.model.resource.human.HumanResourceList;
import fr.groupe4.clientprojet.model.resource.material.MaterialResource;
import fr.groupe4.clientprojet.model.resource.material.MaterialResourceList;
import org.jetbrains.annotations.NotNull;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class EventGestionMaterialConfirm implements ActionListener {
    /**
     * Parent
     */
    @NotNull
    private final DrawDialog parent;

    /**
     * Utilisateurs choisis
     */
    private final boolean[] chosenMats;

    /**
     * Projet courant
     */
    @NotNull
    private final Project project;

    /**
     * Liste des utilisateurs
     */
    private final MaterialResourceList materials;

    /**
     * Constructeur
     *
     * @param parent     Parent
     * @param project    Projet courant
     * @param materials  Matériels
     * @param chosenMats Matériels sélectionnés
     */
    public EventGestionMaterialConfirm(@NotNull DrawDialog parent,
                                    @NotNull Project project,
                                    @NotNull MaterialResourceList materials,
                                    boolean[] chosenMats) {
        this.parent = parent;
        this.project = project;
        this.chosenMats = chosenMats;
        this.materials = materials;
    }

    /**
     * Clic bouton
     *
     * @param actionEvent Event
     */
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        ArrayList<Communication> comms = new ArrayList<>();

        for (int i = 0; i < materials.size(); i++) {
            if (chosenMats[i]) {
                MaterialResource m = materials.get(i);

                Communication c = Communication.builder()
                        .startNow()
                        .addMaterialResourceToProject(project.getId(), m.getResourceId(), null, null)
                        .build();

                comms.add(c);
            }
        }

        for (int i = comms.size() - 1; i >= 0; i--) {
            Communication c = comms.get(i);

            if (!c.isFinished()) {
                c.sleepUntilFinished();
            }

            materials.remove(i);
        }

        new ErrorDialog("Matériels bien ajoutés", "SUCCESS", ErrorDialog.COLOR_OK, parent);
        parent.dispose();
    }
}
