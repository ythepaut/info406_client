package fr.groupe4.clientprojet.display.dialog.taskcreationdialog.view;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import fr.groupe4.clientprojet.display.dialog.controller.GenericExitEvent;
import fr.groupe4.clientprojet.display.dialog.taskcreationdialog.controller.EventTaskCreation;
import fr.groupe4.clientprojet.display.view.draw.DrawDialog;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.time.DayOfWeek;

/**
 * Dialog de création de tâche
 */
public class TaskCreationDialog extends DrawDialog {
    /**
     * Parent
     */
    @Nullable
    private JFrame parent;

    /**
     * Id du projet
     */
    private long projectId;

    /**
     * Constructeur
     *
     * @param parent Parent
     * @param projectId Id du projet
     */
    public TaskCreationDialog(@Nullable JFrame parent, long projectId) {
        this.parent = parent;
        this.projectId = projectId;

        setTitle("Fenêtre de création de tâche");
        setModal(true);

        drawContent();
        setVisible(true);
    }

    /**
     * Affichage
     */
    @Override
    protected void drawContent() {
        setSize(300, 550);
        setResizable(false);
        setUndecorated(true);
        rootPane.setBorder(new MatteBorder(2, 2, 2, 2, Color.BLACK));
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width/2 - getWidth()/2, dim.height/2 - getHeight()/2);

        GridBagConstraints c = new GridBagConstraints();

        /**
         * Déclaration du layout
         */
        this.setLayout(new GridBagLayout());
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        c.gridheight = 1;
        c.insets = new Insets(5,0,5,0);


        /**
         * Entrée du nom et description de projet
         */
        JLabel labelnom = new JLabel("Entrez le nom de la tâche : ");
        add(labelnom,c);
        JTextField nomprojet = new JTextField(15);
        c.gridy++;
        add(nomprojet,c);
        String strnomprojet = nomprojet.getText();

        c.gridy++;
        JLabel labeldescription = new JLabel("Entrez la description de la tâche : ");
        add(labeldescription,c);
        c.gridy++;
        JTextArea description = new JTextArea(18, 22);
        description.setLineWrap(true);
        add(description,c);
        String strdescription = description.getText();

        /**
         * Entrée de la date limite du projet
         */
        c.gridy++;
        JLabel datefinlabel = new JLabel("Entrez la date limite de la tâche : ");
        add(datefinlabel,c);

        DatePickerSettings dateSettings = new DatePickerSettings();
        dateSettings.setFirstDayOfWeek(DayOfWeek.MONDAY);
        DatePicker datePicker = new DatePicker(dateSettings);
        c.gridy++;
        add(datePicker,c);

        /**
         * Création des boutons de confirmation/annulation
         */
        c.gridwidth = 1;
        c.gridy++;
        JButton creeprojet = new JButton("Création Tâche");
        creeprojet.addActionListener(new EventTaskCreation(this, datePicker, nomprojet, description, projectId));
        add(creeprojet,c);
        c.gridx++;
        JButton cancelButton = new JButton("Annuler");
        cancelButton.addActionListener(new GenericExitEvent(this));
        add(cancelButton,c);
    }
}
