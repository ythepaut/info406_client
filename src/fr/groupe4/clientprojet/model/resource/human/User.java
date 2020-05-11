package fr.groupe4.clientprojet.model.resource.human;

import fr.groupe4.clientprojet.logger.Logger;
import fr.groupe4.clientprojet.model.message.Message;
import fr.groupe4.clientprojet.model.resource.human.enums.HumanRole;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Utilisateur
 */
public class User extends HumanResource {
    /**
     * Utilisateur courant
     */
    @Nullable
    private static User user = null;

    /**
     * Récupère l'utilisateur courant
     *
     * @return Utilsiateur
     * @throws IllegalStateException Si l'utilisateur ne s'est pas connecté
     */
    @NotNull
    public static User getUser() throws IllegalStateException {
        if (user == null) {
            Logger.error("User pas encore créé");
            throw new IllegalStateException("User pas encore créé");
        }

        return user;
    }

    /**
     * Construit l'utilisateur
     *
     * @param resource Ressource humaine
     * @param ip       IP
     * @param type     Type
     * @param userId   Id utilisateur
     * @param username Nom d'utilisateur
     * @param email    email
     */
    public static void initUser(@NotNull HumanResource resource,
                                @NotNull String ip,
                                @NotNull String type,
                                long userId,
                                @NotNull String username,
                                @NotNull String email) {
        user = new User(resource, ip, type, userId, username, email);
    }

    /**
     * Adresse IP
     */
    @NotNull
    private final String ip;

    /**
     * Type
     */
    @NotNull
    private final String type;

    /**
     * Id d'utilisateur
     */
    private final long userId;

    /**
     * Nom d'utilisateur
     */
    @NotNull
    private final String username;

    /**
     * Adresse mail
     */
    @NotNull
    private final String email;

    /**
     * Constructeur
     *
     * @param resource Ressource humaine
     * @param ip       IP
     * @param type     Type
     * @param userId   Id utilisateur
     * @param username Nom d'utilisateur
     * @param email    email
     */
    private User(@NotNull HumanResource resource,
                 @NotNull String ip,
                 @NotNull String type,
                 long userId,
                 @NotNull String username,
                 @NotNull String email) {
        super(resource);

        this.ip = ip;
        this.type = type;
        this.userId = userId;
        this.username = username;
        this.email = email;
    }

    /**
     * Récupère l'id de l'utilisateur
     *
     * @return Id
     */
    public long getUserId() {
        return userId;
    }

    /**
     * Récupère l'IP de l'utilisateur
     *
     * @return IP
     */
    @NotNull
    public String getIp() {
        return ip;
    }

    /**
     * Récupère le type de l'utilisateur
     *
     * @return Type
     */
    @NotNull
    public String getType() {
        return type;
    }

    /**
     * Retourne le nom d'utilisateur
     *
     * @return Username
     */
    @NotNull
    public String getUsername() {
        return username;
    }

    /**
     * Retourne l'email
     *
     * @return Email
     */
    @NotNull
    public String getEmail() {
        return email;
    }

    /**
     * Si l'utilisateur peut créer un projet ou non
     *
     * @return Peut ou non créer le projet
     */
    public boolean canCreateProject() {
        return getRole() == HumanRole.RESOURCE_MANAGER;
    }

    /**
     * Si l'utilisateur est l'expéditeur d'un message ou non
     *
     * @param message Message
     * @return Expéditeur ou non
     */
    public boolean isSender(@NotNull Message message) {
        return message.getSrc().getResourceId() == getResourceId();
    }
}
