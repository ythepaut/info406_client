package fr.groupe4.clientprojet.resource.human;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Utilisateur
 */
public class User extends HumanResource {
    @Nullable
    private static User user = null;

    /**
     * Adresse IP
     */
    @NotNull
    private String ip;

    /**
     * Type
     */
    @NotNull
    private String type;

    /**
     * Id d'utilisateur
     */
    private long userId;

    /**
     * Nom d'utilisateur
     */
    @NotNull
    private String username;

    /**
     * Adresse mail
     */
    @NotNull
    private String email;

    /**
     * Constructeur
     *
     * @param resource Ressource humaine
     * @param ip IP
     * @param type Type
     * @param userId Id utilisateur
     * @param username Nom d'utilisateur
     * @param email email
     */
    public User(@NotNull HumanResource resource, @NotNull String ip, @NotNull String type, long userId, String username, String email) {
        super(resource);

        this.ip = ip;
        this.type = type;
        this.userId = userId;
        this.username = username;
        this.email = email;

        user = this;
    }

    public long getUserId() {
        return userId;
    }

    public String getIp() {
        return ip;
    }

    public String getType() {
        return type;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public static User getUser() {
        return user;
    }
}
