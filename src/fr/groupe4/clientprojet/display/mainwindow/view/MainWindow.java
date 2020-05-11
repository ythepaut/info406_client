package fr.groupe4.clientprojet.display.mainwindow.view;

import fr.groupe4.clientprojet.display.mainwindow.controller.EventMainWindow;
import fr.groupe4.clientprojet.display.mainwindow.panels.centerpanel.view.CenterPanel;
import fr.groupe4.clientprojet.display.mainwindow.panels.leftpanel.view.LeftPanel;
import fr.groupe4.clientprojet.logger.Logger;
import fr.groupe4.clientprojet.utils.Location;
import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;

/**
 * Fenêtre principale
 */
public class MainWindow extends JFrame {
    /**
     * Panel central
     */
    @NotNull
    private CenterPanel centerPanel;

    /**
     * Panel de gauche
     */
    @NotNull
    private LeftPanel leftPanel;

    private static MainWindow instance = null;

    /**
     * Constructeur de la fenêtre
     *
     * @param title Titre de la fenêtre
     */
    public MainWindow(@NotNull String title) {
        if (instance == null) instance = this;
        // Définition de la fenêtre
        setTitle(title);
        try {
            setIconImage(ImageIO.read(new File(Location.getPath() + "/data/img/icon.png")));
        } catch (IOException e) {
            Logger.error(e.getMessage());
        }
        setMinimumSize(new Dimension(1400, 800));
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width/2 - getWidth()/2, dim.height/2 - getHeight()/2);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new EventMainWindow(this));

        drawContent();

        setVisible(true);
    }

    /**
     * Dessine le contenu de la fenêtre
     */
    private void drawContent() {
        setLayout(new BorderLayout());
        centerPanel = new CenterPanel(CenterPanel.USER);
        //! Dans LeftPanel, le bouton user est initialisé comme selected
        leftPanel = new LeftPanel(centerPanel, this);
        add(leftPanel, BorderLayout.WEST);
        add(centerPanel, BorderLayout.CENTER);
    }

    /**
     * Récupère le panel central
     *
     * @return Panel central
     */
    @NotNull
    public CenterPanel getCenterPanel() {
        return centerPanel;
    }

    /**
     * Récupère le panel de gauche
     *
     * @return Panel de gauche
     */
    @NotNull
    public LeftPanel getLeftPanel() {
        return leftPanel;
    }

    public static MainWindow getInstance() {
        return instance;
    }
}
