package fr.groupe4.clientprojet.model.parameters;

import fr.groupe4.clientprojet.logger.Logger;
import fr.groupe4.clientprojet.utils.Location;

import java.io.*;
import java.util.HashMap;

/**
 * Classe abstraite qui contient les paramètres de l'application
 */
public abstract class Parameters {
    private final static String fileName = "/parameters.set";
    /**
     * Liste des paramètres avec leurs valeurs
     * ParametersName -> Object
     * Nom paramètre  -> valeur
     *
     * La liste des paramètres est dans une énumération
     * @see ParametersNames
     */
    private static HashMap<ParametersNames, Object> parameters;

    /**
     * <!> Obligatoire </!>
     * Permet de récuperer tous les paramètres stocké dans le fichier
     */
    public static void init() {
        parameters = new HashMap<>();

        try {
            ObjectInputStream inputStream = new ObjectInputStream(
                    new FileInputStream(Location.getDataPath() + fileName));
            parameters = (HashMap<ParametersNames, Object>) inputStream.readObject();
            inputStream.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * <!> Obligatoire </!>
     * Permet de sauvegarder tous les paramètres dans le fichier
     */
    public static void exit() {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    new FileOutputStream(Location.getDataPath() + fileName));
            outputStream.writeObject(parameters);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // _.-._.-._.-._.-._.-._.-._.-._.-._.-._.-._.-._.-._.-._.-._.-._.-._.-._.-._.-._.-.
    // Getters et Setters

    public static String getServerUrl() {
        return (String) parameters.get(ParametersNames.SERVERURL);
    }

    public static void setServerUrl(String serverUrl) {
        parameters.put(ParametersNames.SERVERURL, serverUrl);
    }

    public static boolean isFirstRun() {
        return (boolean) parameters.get(ParametersNames.FIRSTRUN);
    }

    public static void setFirstRun(boolean firstRun) {
        parameters.put(ParametersNames.FIRSTRUN, firstRun);
    }
}
