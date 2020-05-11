package fr.groupe4.clientprojet.model.parameters.themes;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

/**
 * La couleur du thème
 */
public enum ThemeName implements Serializable {
    CLAIR("CLAIR"),
    SOMBRE("SOMBRE"),
    ;

    /**
     * Version
     */
    private static final long serialVersionUID = 549358439;

    /**
     * Nom
     */
    @NotNull
    final String name;

    /**
     * Constructeur
     *
     * @param name Nom
     */
    ThemeName(@NotNull String name) {
        this.name = name.toUpperCase();
    }
}
