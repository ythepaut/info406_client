package fr.groupe4.clientprojet.communication;

public class Task {
    private String desc;
    private int id;

    public Task(String id) {
        if (id.equals("")) {
            this.id = -1;
        } else {
            this.id = Integer.parseInt(id);
        }
    }

    public void setDescription(String desc) {
        this.desc = desc;
    }
}
