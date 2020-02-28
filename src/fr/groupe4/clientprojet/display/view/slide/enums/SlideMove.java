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
        return this.name;
    }

    /**
     * Renvoie l'enum correspondant au nom
     *
     * @param name : le nom
     * @return : l'enum
     */
    public static SlideMove getEnum(String name) {
        SlideMove res = null;

        switch (name) {
            case "left":
                res = LEFT;
                break;

            case "right":
                res = RIGHT;
                break;

            default:
        }

        return res;
    }
}
