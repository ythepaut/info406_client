package fr.groupe4.clientprojet.model.parameters.themes;

import fr.groupe4.clientprojet.model.parameters.Parameters;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

/**
 * Les différentes couleurs pour les thèmes
 */
public enum Theme {
    /**
     * Le fond
     */
    FOND(
            Color.WHITE,
            Color.BLACK
    ),

    /**
     * Fond des champs de texte
     */
    FOND_FIELD(
            new Color(220, 220, 220),
            new Color(30, 30, 30)
    ),

    /**
     * Police normale
     */
    POLICE_NORMAL(
            Color.BLACK,
            Color.WHITE
    ),

    /**
     * Police d'erreurs
     */
    POLICE_ERROR(
            Color.RED,
            Color.RED
    ),

    /**
     * Police accentuée
     */
    POLICE_ACCENT(
            new Color(0, 0, 50),
            new Color(0, 0, 200)
    ),

    /**
     * Bordures
     */
    BORDER(
            Color.LIGHT_GRAY,
            Color.DARK_GRAY
    ),

    /**
     * Boutons
     */
    FOND_BUTTON(
            Color.LIGHT_GRAY,
            Color.DARK_GRAY
    ),

    /**
     * RoundButton sélectionné
     */
    BUTTON_SELECTED(
            new Color(84, 180, 255),
            new Color(42, 90, 128)
    ),

    /**
     * Message envoyé
     */
    MESSAGE_SENT(
            new Color(200, 200, 200),
            new Color(55, 55, 55)
    ),

    /**
     * Message reçu
     */
    MESSAGE_RECEIVED(
            new Color(240, 240, 240),
            new Color(15, 15, 15)
    );

    /**
     * Couleur pour le thème clair
     */
    @NotNull
    private final Color clair;

    /**
     * Couleur pour le thème sombre
     */
    @NotNull
    private final Color sombre;

    /**
     * Constructeur
     *
     * @param clair  Couleur du thème clair
     * @param sombre Couleur du thème sombre
     */
    Theme(@NotNull Color clair, @NotNull Color sombre) {
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
        Color res = clair;

        switch (name) { // Switch pour quand on rajoutera des thèmes
            case SOMBRE:
                res = sombre;
                break;

            default:
        }

        return res;
    }

    public Color getColor() {
        return getColor(Parameters.getThemeName());
    }
}
