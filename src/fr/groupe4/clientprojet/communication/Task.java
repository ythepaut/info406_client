package fr.groupe4.clientprojet.communication;

/**
 * Tâche
 *
 * @author Romain
 */
public class Task {
    /**
     * Description de la tâche
     */
    private String desc;

    /**
     * ID de la tâche
     */
    private int id;

    public Task(String id) {
        if (id.equals("")) {
            this.id = -1;
        } else {
            this.id = Integer.parseInt(id);
        }
    }

    /**
     * Setter de la description
     */
    public void setDescription(String desc) {
        this.desc = desc;
    }
}
