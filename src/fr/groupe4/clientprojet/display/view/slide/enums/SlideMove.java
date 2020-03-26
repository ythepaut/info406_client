package fr.groupe4.clientprojet.display.view.slide.enums;

/**
 * Enumération pour les boutons gauches et droites
 */
public enum SlideMove {
    LEFT("left"),
    RIGHT("right");

    /**
     * Le nom du bouton
     */
    private final String name;

    /**
     * Le constructeur
     *
     * @param name : le nom du bouton
     */
    SlideMove(String name) {
        this.name = name;
    }

    /**
     * Renvoie le nom du bouton
     *
     * @return : le nom
     */
    public String getName() {
        return name;
    }
}
