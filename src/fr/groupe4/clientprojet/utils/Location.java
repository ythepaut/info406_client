package fr.groupe4.clientprojet.utils;

/**
 * Classe abstraite qui permet de connaître le répertoire de l'éxécutable via sa méthode statique getPath();
 */
public abstract class Location {

    /**
     * Renvoie le répertoire de l'exécutable java
     * Renvoie "/" si non trouvé
     *
     * @return String : sous la forme "blabla/"
     */
    public static String getPath() {
        String path = System.getProperty("java.class.path");

        path = path.split(System.getProperty("path.separator"))[0];

        return path;
    }
}
