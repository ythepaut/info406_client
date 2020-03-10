package fr.groupe4.clientprojet.model.resource.human.enums;

import fr.groupe4.clientprojet.logger.Logger;
import fr.groupe4.clientprojet.logger.enums.LoggerOption;
import org.jetbrains.annotations.NotNull;

public enum HumanRole {
    RESOURCE_MANAGER("RESOURCE_MANAGER"),
    PROJECT_LEADER("PROJECT_LEADER"),
    COLLABORATOR("COLLABORATOR");

    private String role;

    HumanRole(String role) {
        this.role = role;
    }

    @NotNull
    public static HumanRole fromString(String role) throws IllegalArgumentException {
        HumanRole[] vars = HumanRole.values();

        HumanRole result = null;

        for (HumanRole var : vars) {
            if (var.role.equalsIgnoreCase(role)) {
                result = var;
            }
        }

        if (result == null) {
            String errorMsg = "Pas d'enum provenant de la chaine '" + role + "'";
            Logger.error(errorMsg, LoggerOption.LOG_FILE_ONLY);
            throw new IllegalArgumentException(errorMsg);
        }

        return result;
    }

    @Override
    public String toString() {
        return role;
    }
}
