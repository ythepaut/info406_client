package fr.groupe4.clientprojet.display.dialog.usersgestiondialog.controller;

import fr.groupe4.clientprojet.display.view.draw.DrawDialog;
import fr.groupe4.clientprojet.model.resource.human.HumanResource;
import org.jetbrains.annotations.NotNull;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class EventChoixUser implements ItemListener {
    /**
     * Source parente
     */
    private DrawDialog source;

    /**
     * Utilisateur courant
     */
    private final HumanResource currentUser;

    /**
     * Utilisateurs sélectionnés
     */
    private boolean[] selectedUsers;

    /**
     * Id dans l'ArrayList contenant les utilisateurs
     */
    private final int idArrayList;

    /**
     * Constructeur
     *
     * @param source        Source
     * @param currentUser   Utilisateur courant
     * @param selectedUsers Tableau si l'utilisateur a été sélectionné ou non
     * @param idArrayList   Id dans l'ArrayList contenant les utilisateurs
     */
    public EventChoixUser(@NotNull DrawDialog source,
                          @NotNull HumanResource currentUser,
                          boolean[] selectedUsers,
                          int idArrayList) {
        this.source = source;
        this.currentUser = currentUser;
        this.selectedUsers = selectedUsers;
        this.idArrayList = idArrayList;
    }

    /**
     * Case cochée ou décochée
     *
     * @param itemEvent Event
     */
    @Override
    public void itemStateChanged(ItemEvent itemEvent) {
        selectedUsers[idArrayList] = (itemEvent.getStateChange() == ItemEvent.SELECTED);
    }
}
