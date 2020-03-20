package fr.groupe4.clientprojet.utils;

import org.jetbrains.annotations.NotNull;

/**
 * Classe abstraite qui permet de connaître le répertoire de l'éxécutable via sa méthode statique getPath();
 */
public abstract class Location {

    /**
     * Renvoie le répertoire de l'exécutable java
     * Renvoie "/" si non trouvé
     *
     * @return String : sous la forme "blabla"
     */
    @NotNull
    public static String getPath() {
        String path = System.getProperty("java.class.path");

        path = path.split(System.getProperty("path.separator"))[0];

        return path;
    }

    /**
     * Renvoie le répertoire data
     *
     * @return String
     */
    @NotNull
    public static String getDataPath() {
        return getPath() + "/data";
    }

    /**
     * Renvoie le répertoire img
     *
     * @return String
     */
    @NotNull
    public static String getImgDataPath() {
        return getDataPath() + "/img";
    }
}
