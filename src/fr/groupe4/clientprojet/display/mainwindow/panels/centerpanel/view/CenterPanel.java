package fr.groupe4.clientprojet.display.mainwindow.panels.centerpanel.view;

import javax.swing.*;
import java.awt.*;

import fr.groupe4.clientprojet.display.mainwindow.panels.calendarpanel.view.CalendarPanel;
import fr.groupe4.clientprojet.display.mainwindow.panels.projectpanel.view.ProjectPanel;
import fr.groupe4.clientprojet.display.mainwindow.panels.userpanel.view.UserPanel;

/**
 * Créé le panel du centre de la fenêtre
 */
public class CenterPanel extends JPanel {
    /**
     * Valeurs que peut prendre la vue
     * Une dernière valeur est possible, dans ce cas c'est le nom du projet
     */
    public static final String CALENDAR = "calendar", USER = "user";
    /**
     * La vue sur laquelle on est
     */
    private String view;

    /**
     * Le constructeur
     *
     * @param view : la vue sur laquelle se mettre
     */
    public CenterPanel(String view) {
        this.view = view;

        setBackground(Color.WHITE);

        drawContent();
    }

    /**
     * Définie la vue du panel central
     *
     * @param view : la vue
     */
    public void setView(String view) {
        this.view = view;

        // On redéssine
        redraw();
    }

    /**
     * Renvoie la vue sur laquelle on est
     *
     * @return : la vue
     */
    public String getView() {
        return view;
    }

    /**
     * Dessine le contenu du panel
     */
    private void drawContent() {
        setLayout(new BorderLayout());
        switch (view) {
            case CALENDAR:
                add(new CalendarPanel(), BorderLayout.CENTER);
                break;

            case USER:
                add(new UserPanel(), BorderLayout.CENTER);
                break;

            default:
                add(new ProjectPanel(view), BorderLayout.CENTER);
        }
    }

    /**
     * Redessine le panel
     */
    public void redraw() {
        removeAll();
        validate();
        revalidate();
        repaint();
        drawContent();
    }
}
