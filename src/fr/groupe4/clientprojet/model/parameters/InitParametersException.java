package fr.groupe4.clientprojet.model.parameters;

public class InitParametersException extends IllegalStateException {

    public InitParametersException() {
        super("Les paramètres n'ont pas été initialisés");
    }
}
