package fr.groupe4.clientprojet.communication.enums;

import fr.groupe4.clientprojet.logger.Logger;
import fr.groupe4.clientprojet.logger.enums.LoggerOption;

/**
 * Codes réponse HTML
 */
public enum HTMLCode {
    HTML_CUSTOM_DEFAULT_ERROR(-1),
    HTML_CUSTOM_TIMEOUT(-2),
    HTML_CUSTOM_CANCEL(-3),
    HTML_OK(200),
    HTML_BAD_REQUEST(400),
    HTML_UNAUTHORIZED(401),
    HTML_FORBIDDEN(403),
    HTML_NOT_FOUND(404),
    HTML_TIMEOUT(408);

    /**
     * Équivalent entier
     */
    private int code;

    /**
     * Constructeur
     *
     * @param code Code associé
     */
    HTMLCode(int code) {
        this.code = code;
    }

    /**
     * Retourne le code associé
     *
     * @return Code
     */
    public int getCode() {
        return code;
    }

    /**
     * Renvoie l'énum associée à un code entier
     *
     * @param code Code
     * @return Énum
     */
    public static HTMLCode fromInt(int code) throws IllegalArgumentException {
        HTMLCode[] vars = HTMLCode.values();

        HTMLCode result = null;

        for (HTMLCode var : vars) {
            if (var.code == code) {
                result = var;
            }
        }

        if (result == null) {
            String errorMsg = "Pas d'enum provenant de la chaine '" + code + "'";
            Logger.error(errorMsg, LoggerOption.LOG_FILE_ONLY);
            throw new IllegalArgumentException(errorMsg);
        }

        return result;
    }

    @Override
    public String toString() {
        return name() + "(" + code + ")";
    }
}
