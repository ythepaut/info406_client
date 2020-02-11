package fr.groupe4.clientprojet.utils;

public abstract class Location {

    /**
     * Renvoie le répertoire de l'exécutable java
     * Renvoie "/" si non trouvé
     *
     * @return String : sous la forme "blabla/"
     */
    public static String getPath() {
        String path = System.getProperty("java.class.path");

        path = path.split(";")[0];

        return path;
    }
}
