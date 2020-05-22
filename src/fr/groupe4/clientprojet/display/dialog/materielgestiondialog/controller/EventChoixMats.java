package fr.groupe4.clientprojet.display.dialog.materielgestiondialog.controller;

import fr.groupe4.clientprojet.display.dialog.materielgestiondialog.view.MaterielGestionDialog;
import fr.groupe4.clientprojet.display.view.draw.DrawDialog;
import fr.groupe4.clientprojet.model.resource.human.HumanResource;
import fr.groupe4.clientprojet.model.resource.material.MaterialResource;
import org.jetbrains.annotations.NotNull;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class EventChoixMats implements ItemListener {
    /**
     * Source parente
     */
    private DrawDialog source;

    /**
     * Utilisateur courant
     */
    private final MaterialResource currentMat;

    /**
     * Utilisateurs sélectionnés
     */
    private boolean[] selectedMats;

    /**
     * Id dans l'ArrayList contenant les utilisateurs
     */
    private final int idArrayList;

    /**
     * Constructeur
     *
     * @param source        Source
     * @param currentMat   Utilisateur courant
     * @param selectedMats Tableau si l'utilisateur a été sélectionné ou non
     * @param idArrayList   Id dans l'ArrayList contenant les utilisateurs
     */
    public EventChoixMats(@NotNull DrawDialog source,
                          @NotNull MaterialResource currentMat,
                          boolean[] selectedMats,
                          int idArrayList) {
        this.source = source;
        this.currentMat = currentMat;
        this.selectedMats = selectedMats;
        this.idArrayList = idArrayList;
    }

    /**
     * Case cochée ou décochée
     *
     * @param itemEvent Event
     */
    @Override
    public void itemStateChanged(ItemEvent itemEvent) {
        selectedMats[idArrayList] = (itemEvent.getStateChange() == ItemEvent.SELECTED);
    }
}
