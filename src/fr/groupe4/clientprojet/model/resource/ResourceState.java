package fr.groupe4.clientprojet.model.resource;

import org.jetbrains.annotations.NotNull;

import fr.groupe4.clientprojet.logger.Logger;

public enum ResourceState {
    ALLOCATED("ALLOCATED");

    @NotNull
    private final String name;

    ResourceState(@NotNull String name) {
        this.name = name;
    }

    @NotNull
    @Override
    public String toString() {
        return name;
    }

    public static ResourceState getEnum(String name) {
        if (name.equalsIgnoreCase(ALLOCATED.name)) {
            return ALLOCATED;
        }
        else {
            Logger.error(name);
            throw new IllegalStateException("Enum inexistante");
        }
    }
}

