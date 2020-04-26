package fr.groupe4.clientprojet.display.view.slide.enums;

import org.jetbrains.annotations.NotNull;

/**
 * Enumération pour les boutons gauches et droites
 */
public enum SlideMove {
    LEFT("left"),
    RIGHT("right");

    /**
     * Nom du bouton
     */
    @NotNull
    private final String name;

    /**
     * Le constructeur
     *
     * @param name Nom du bouton
     */
    SlideMove(@NotNull String name) {
        this.name = name;
    }

    /**
     * Renvoie le nom du bouton
     *
     * @return Nom
     */
    @NotNull
    public String getName() {
        return name;
    }
}
