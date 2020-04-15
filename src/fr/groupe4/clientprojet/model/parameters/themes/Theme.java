package fr.groupe4.clientprojet.model.parameters.themes;

import java.awt.*;

/**
 * Les différentes couleurs pour les thèmes
 */
public enum Theme {
    FOND(
            Color.WHITE,
            Color.BLACK
    ), // Le fond
    FOND_FIELD(
            new Color(220, 220, 220),
            new Color(30, 30, 30)
    ), // Fond des champs texte
    POLICE_NORMAL(
            Color.BLACK,
            Color.WHITE
    ), // La police normale
    POLICE_ERROR(
            Color.RED,
            Color.RED
    ), // Les erreurs
    POLICE_ACCENT(
            new Color(0, 0, 50),
            new Color(0, 0, 200)
    ), // Les accentuation
    BORDER(
            Color.LIGHT_GRAY,
            Color.DARK_GRAY
    ), // Les bordures
    FOND_BUTTON(
            Color.LIGHT_GRAY,
            Color.DARK_GRAY
    ), // Les boutons
    BUTTON_SELECTED(
            new Color(84, 180, 255),
            new Color(42, 90, 128)
    ), // RoundButton sélectionné
    MESSAGE_SENT(
            new Color(200, 200, 200),
            new Color(55, 55, 55)
    ), // Message envoyé
    MESSAGE_RECEIVED(
            new Color(240, 240, 240),
            new Color(15, 15, 15)
    ), // Message reçu
    ;

    /**
     * La couleur pour le thème clair
     */
    private Color clair;
    /**
     * La couleur pour le thème sombre
     */
    private Color sombre;

    Theme(Color clair, Color sombre) {
        this.clair = clair;
        this.sombre = sombre;
    }

    /**
     * Renvoie la couleur correspondante au nom du thème
     *
     * @param name Le nom du thème
     * @return Color
     */
    public Color getColor(ThemeName name) {
        Color res = null;

        switch (name) {
            case CLAIR:
                res = this.clair;
                break;

            case SOMBRE:
                res = this.sombre;
                break;

            default:
        }

        return res;
    }
}
