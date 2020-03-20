package fr.groupe4.clientprojet.model.parameters;

import java.io.Serializable;

/**
 * Enumeration pour les noms des paramètres
 */
public enum ParametersNames implements Serializable {
    SERVERURL, // String  | Adresse du serveur auquel on se connecte
    FIRSTRUN, //  boolean | Si c'est la première fois qu'on lance l'application
    ;

    private static final long serialVersionUID = 434052303;

    ParametersNames() {}
}
