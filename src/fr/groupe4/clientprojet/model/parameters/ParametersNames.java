package fr.groupe4.clientprojet.model.parameters;

import java.io.Serializable;

/**
 * Enumeration pour les noms des paramètres
 */
public enum ParametersNames implements Serializable {
    /**
     * URL du serveur
     */
    SERVERURL,

    /**
     * Premier lancement ou non
     */
    FIRSTRUN,

    /**
     * Couleur du thème
     *
     * @see fr.groupe4.clientprojet.model.parameters.themes.ThemeName
     */
    THEME;

    /**
     * Version pour la sérialisation
     */
    private static final long serialVersionUID = 434052303;
}
