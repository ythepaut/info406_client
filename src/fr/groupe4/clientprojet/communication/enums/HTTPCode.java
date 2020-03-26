package fr.groupe4.clientprojet.communication.enums;

import fr.groupe4.clientprojet.logger.Logger;
import fr.groupe4.clientprojet.logger.enums.LoggerOption;

/**
 * Codes réponse HTML
 */
public enum HTTPCode {
    HTTP_CUSTOM_DEFAULT_ERROR(-1),
    HTTP_CUSTOM_TIMEOUT(-2),
    HTTP_CUSTOM_CANCEL(-3),
    HTTP_OK(200),
    HTTP_BAD_REQUEST(400),
    HTTP_UNAUTHORIZED(401),
    HTTP_FORBIDDEN(403),
    HTTP_NOT_FOUND(404),
    HTTP_TIMEOUT(408),
    HTTP_TOO_MANY_REQUESTS(429);

    /**
     * Équivalent entier
     */
    private int code;

    /**
     * Constructeur
     *
     * @param code Code associé
     */
    HTTPCode(int code) {
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
    public static HTTPCode fromInt(int code) throws IllegalArgumentException {
        HTTPCode[] vars = HTTPCode.values();

        HTTPCode result = null;

        for (HTTPCode var : vars) {
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
