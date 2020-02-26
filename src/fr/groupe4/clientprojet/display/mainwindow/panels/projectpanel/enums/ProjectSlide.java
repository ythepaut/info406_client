package fr.groupe4.clientprojet.display.mainwindow.panels.projectpanel.enums;

/**
 * L'enumeration pour les slides du panel projet
 */
public enum ProjectSlide {
    HOME(0),
    TASK(1),
    MESSAGE(2);

    /**
     * Le numéro du slide
     */
    private int nb;

    /**
     * Le contructeur
     *
     * @param nb : le numéro du slide
     */
    ProjectSlide(int nb) {
        this.nb = nb;
    }

    /**
     * Renvoie le numéro du slide
     *
     * @return : le numéro du slide
     */
    public int getNb() {
        return this.nb;
    }

    /**
     * Renvoie le numéro du slide en String
     *
     * @return : le numéro du slide
     */
    public String getNbString() {
        return Integer.toString(this.nb);
    }

    /**
     * Renvoie l'énumération correspondant au numéro
     *
     * @param nb : le numéro
     * @return : l'énumération
     */
    public static ProjectSlide getEnum(int nb) {
        ProjectSlide res = null;

        switch (nb) {
            case 0:
                res = HOME;
                break;

            case 1:
                res = TASK;
                break;

            case 2:
                res = MESSAGE;
                break;

            default:
        }

        return res;
    }
}
