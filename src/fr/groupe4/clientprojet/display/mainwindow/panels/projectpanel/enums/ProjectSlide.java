package fr.groupe4.clientprojet.display.mainwindow.panels.projectpanel.enums;

public enum ProjectSlide {
    HOME(0),
    TASK(1),
    MESSAGE(2);

    private int nb;

    ProjectSlide(int nb) {
        this.nb = nb;
    }

    public int getNb() {
        return this.nb;
    }

    public String getNbString() {
        return Integer.toString(this.nb);
    }

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
