package fr.groupe4.clientprojet.model.parameters.themes;

import java.io.Serializable;

/**
 * La couleur du thème
 */
public enum ThemeName implements Serializable {
    CLAIR,
    SOMBRE,
    ;

    private static final long serialVersionUID = 549358439;

    ThemeName() {}

    public static ThemeName getInstance(String name) {
        ThemeName res = null;

        switch (name) {
            case "Clair":
                res = CLAIR;
                break;

            case "Sombre":
                res = SOMBRE;
                break;

            default:
        }
        return res;
    }
}
