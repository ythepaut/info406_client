package fr.groupe4.clientprojet.model.parameters;

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
            System.err.println("Le fichier 'parameters.set' n'existe pas !");

            // Définition des paramètres par défaut
            parameters.put(ParametersNames.FIRSTRUN, true);
            parameters.put(ParametersNames.SERVERURL, "https://api.ythepaut.com/g4/actions");
        }
    }

    /**
     * <!> Obligatoire </!>
     * Permet de sauvegarder tous les paramètres dans le fichier
     */
    public static void exit() {
        try {
            if (parameters == null) throw new InitParametersException(); // Si init() n'a pas été appelé

            ObjectOutputStream outputStream = new ObjectOutputStream(
                    new FileOutputStream(Location.getDataPath() + fileName));
            outputStream.writeObject(parameters);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InitParametersException e) {
            System.err.println(e.getMessage());
        }
    }

    // _.-._.-._.-._.-._.-._.-._.-._.-._.-._.-._.-._.-._.-._.-._.-._.-._.-._.-._.-._.-.
    // Getters et Setters

    public static String getServerUrl() {
        String res = "";
        try {
            if (parameters == null) throw new InitParametersException();
            res = (String) parameters.get(ParametersNames.SERVERURL);
        } catch (InitParametersException e) {
            System.err.println(e.getMessage());
        }
        return res;
    }

    public static void setServerUrl(String serverUrl) {
        try {
            if (parameters == null) throw new InitParametersException();
            parameters.put(ParametersNames.SERVERURL, serverUrl);
        } catch (InitParametersException e) {
            System.err.println(e.getMessage());
        }
    }

    public static boolean isFirstRun() {
        boolean res = true;
        try {
            if (parameters == null) throw new InitParametersException();
            res =  (boolean) parameters.get(ParametersNames.FIRSTRUN);
        } catch (InitParametersException e) {
            System.err.println(e.getMessage());
        }
        return res;
    }

    public static void setFirstRun(boolean firstRun) {
        try {
            if (parameters == null) throw new InitParametersException();
            parameters.put(ParametersNames.FIRSTRUN, firstRun);
        } catch (InitParametersException e) {
            System.err.println(e.getMessage());
        }
    }
}
