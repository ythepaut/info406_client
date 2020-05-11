package fr.groupe4.clientprojet.model.parameters.themes;

import fr.groupe4.clientprojet.logger.Logger;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

/**
 * La couleur du thème
 */
public enum ThemeName implements Serializable {
    CLAIR("CLAIR"),
    SOMBRE("SOMBRE"),
    DEFAULT("");

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

    /**
     * Récupère l'instance
     *
     * @param name Nom
     * @return Instance
     * @throws IllegalArgumentException Si l'enum n'existe pas
     */
    @NotNull
    public static ThemeName getInstance(String name) throws IllegalArgumentException {
        ThemeName res = null;
        ThemeName[] vars = ThemeName.values();

        for (ThemeName var : vars) {
            if (var.name.equalsIgnoreCase(name)) {
                res = var;
            }
        }

        if (res == null || res == DEFAULT) {
            Logger.error("Erreur sur l'enum", name);
            throw new IllegalArgumentException("Erreur sur l'enum");
        }

        return res;
    }
}
