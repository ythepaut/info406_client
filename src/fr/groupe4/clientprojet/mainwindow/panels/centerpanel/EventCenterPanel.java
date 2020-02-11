package fr.groupe4.clientprojet.mainwindow.panels.centerpanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EventCenterPanel implements ActionListener {
    private CenterPanel owner;

    public static final String LEFT = "left";
    public static final String RIGHT = "right";
    public static final String HOME = "0";
    public static final String TASK = "1";
    public static final String MESSAGE = "2";

    public EventCenterPanel(CenterPanel owner) {
        this.owner = owner;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case LEFT:
                owner.setSlide(owner.getSlide() -1);
                break;

            case RIGHT:
                owner.setSlide(owner.getSlide() +1);
                break;

            case HOME:
                owner.setSlide(0);
                break;

            case TASK:
                owner.setSlide(1);
                break;

            case MESSAGE:
                owner.setSlide(2);
                break;

            default:
        }
        owner.redraw();
    }
}
