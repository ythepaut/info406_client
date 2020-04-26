package fr.groupe4.clientprojet.display.view.slide;

import org.jetbrains.annotations.NotNull;

import javax.swing.JPanel;

/**
 * Élément des slides
 */
public class SlideItem {
    /**
     * Nom de la Frame
     */
    @NotNull
    private final String name;

    /**
     * Frame
     */
    @NotNull
    private final JPanel panel;

    /**
     * Constructeur
     *
     * @param name Nom
     * @param panel Panel
     */
    public SlideItem(@NotNull String name, @NotNull JPanel panel) {
        this.name = name;
        this.panel = panel;
    }

    /**
     * Récupère le nom
     *
     * @return Nom
     */
    @NotNull
    public String getName() {
        return name;
    }

    /**
     * Récupère le panel
     *
     * @return Panel
     */
    @NotNull
    public JPanel getPanel() {
        return panel;
    }
}
