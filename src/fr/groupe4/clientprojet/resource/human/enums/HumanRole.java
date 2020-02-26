package fr.groupe4.clientprojet.resource.human.enums;

public enum HumanRole {
    RESOURCE_MANAGER("RESOURCE_MANAGER"),
    PROJECT_LEADER("PROJECT_LEADER"),
    COLLABORATOR("COLLABORATOR");

    private String role;

    HumanRole(String role) {
        this.role = role;
    }

    public static HumanRole fromString(String role) {
        HumanRole[] vars = HumanRole.values();

        HumanRole result = null;

        for (HumanRole var : vars) {
            if (var.role.equals(role)) {
                result = var;
            }
        }

        return result;
    }

    @Override
    public String toString() {
        return role;
    }
}
