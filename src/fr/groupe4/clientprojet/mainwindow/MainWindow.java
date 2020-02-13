package fr.groupe4.clientprojet.mainwindow;

import fr.groupe4.clientprojet.mainwindow.panels.centerpanel.CenterPanel;
import fr.groupe4.clientprojet.mainwindow.panels.leftpanel.LeftPanel;
import fr.groupe4.clientprojet.utils.Location;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Fenêtre principale
 */
public class MainWindow extends JFrame {

    /**
     * Constructeur de la fenêtre
     *
     * @param title : titre de la fenêtre
     */
    public MainWindow(String title) {
        EventMainWindow eventMainWindow = new EventMainWindow(this);

        // Définition de la fenêtre
        setTitle(title);
        try {
            setIconImage(ImageIO.read(new File(Location.getPath() + "/fr/groupe4/clientprojet/data/img/icon.png")));
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        setMinimumSize(new Dimension(1400, 800));
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width/2 - getWidth()/2, dim.height/2 - getHeight()/2);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

        addWindowListener(eventMainWindow);

        drawContent();

        setVisible(true);
    }

    /**
     * Dessine le contenue de la fenêtre
     */
    private void drawContent() {
        setLayout(new BorderLayout());
        CenterPanel centerPanel = new CenterPanel(CenterPanel.USER); // <!> Dans LeftPanel, le bouton user est initialisé comme selected <!>
        add(new LeftPanel(centerPanel), BorderLayout.WEST);
        add(centerPanel, BorderLayout.CENTER);
    }
}
