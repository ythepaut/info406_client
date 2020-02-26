package fr.groupe4.clientprojet.resource.human;

/**
 * Utilisateur
 */
public class User extends HumanResource {
    /**
     * Adresse IP
     */
    private String ip;

    /**
     * Type
     */
    private String type;

    /**
     * Id d'utilisateur
     */
    private long userId;

    /**
     * Nom d'utilisateur
     */
    private String username;

    /**
     * Adresse mail
     */
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
    public User(HumanResource resource, String ip, String type, long userId, String username, String email) {
        super(resource);

        this.ip = ip;
        this.type = type;
        this.userId = userId;
        this.username = username;
        this.email = email;
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
}
